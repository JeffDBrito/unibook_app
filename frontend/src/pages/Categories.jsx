import { useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import AppLayout from "../components/layout/AppLayout";
import Table from "../components/Table";

import { api } from "../services/api";
import { useAuth } from "../hooks/useAuth";
import { usePaginatedTable } from "../hooks/usePaginatedTable";

export default function Categories({ title }) {
	const navigate = useNavigate();
	const { user } = useAuth();

	const canCreate =
		user?.roles?.includes("ADMIN") ||
		user?.roles?.includes("SUPER_ADMIN");

	const canEdit = canCreate;

	const canDelete =
		user?.roles?.includes("SUPER_ADMIN");

	const fetchCategories = useCallback(
		async ({ page, size, search }) => {
			const params = new URLSearchParams({
				page: String(page),
				size: String(size),
			});

			if (search) {
				params.set("search", search);
			}

			const response = await api(
				`/categories?${params.toString()}`
			);

			if (!response.ok) {
				const body = await response
					.json()
					.catch(() => null);

				throw new Error(
					body?.general ||
					body?.message ||
					"Error when loading categories"
				);
			}

			return response.json();
		},
		[]
	);

	const {
		data: categories,
		setData: setCategories,

		loading,
		error,

		page,
		totalPages,

		search,
		setSearch,

		setPage,
	} = usePaginatedTable(fetchCategories);

	function handleEdit(category) {
		navigate(`/categories/${category.id}/edit`);
	}

	async function handleDelete(category) {
		const confirmed = window.confirm(
			`Delete category "${category.title}"?`
		);

		if (!confirmed) {
			return;
		}

		try {
			const response = await api(
				`/categories/${category.id}`,
				{
					method: "DELETE",
				}
			);

			if (!response.ok) {
				const body = await response
					.json()
					.catch(() => null);

				throw new Error(
					body?.general ||
					body?.message ||
					"Error when deleting category"
				);
			}

			toast.success(
				"Category deleted successfully"
			);

			if (categories.length === 1 && page > 0) {
				setPage(page - 1);
			} else {
				setCategories(currentCategories =>
					currentCategories.filter(
						currentCategory =>
							currentCategory.id !== category.id
					)
				);
			}
		} catch (err) {
			toast.error(
				err.message ||
					"Error when deleting category"
			);
		}
	}

	const columns = [
		{
			key: "title",
			label: "Title",
			accessor: "title",
		},
		{
			key: "description",
			label: "Description",
			render: category =>
				category.description ?? "-",
		},
		{
			key: "actions",
			label: "Actions",
			render: category => (
				<div className="d-flex gap-2">
					{canEdit && (
						<button
							type="button"
							className="btn btn-sm btn-primary"
							onClick={() =>
								handleEdit(category)
							}
						>
							Edit
						</button>
					)}

					{canDelete && (
						<button
							type="button"
							className="btn btn-sm btn-danger"
							onClick={() =>
								handleDelete(category)
							}
						>
							Delete
						</button>
					)}
				</div>
			),
		},
	];

	return (
		<AppLayout title={title}>
			<div className="d-flex justify-content-between align-items-center mb-3">
				<h2 className="mb-0">
					Categories List
				</h2>

				{canCreate && (
					<button
						type="button"
						className="btn btn-primary"
						onClick={() =>
							navigate("/categories/create")
						}
					>
						Create Category
					</button>
				)}
			</div>

			<Table
				columns={columns}
				data={categories}
				loading={loading}
				error={error}
				search={search}
				onSearchChange={setSearch}
				searchPlaceholder="Search by title or description..."
				page={page}
				totalPages={totalPages}
				onPageChange={setPage}
			/>
		</AppLayout>
	);
}