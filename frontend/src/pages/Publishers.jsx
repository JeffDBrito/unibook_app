import { useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import AppLayout from "../components/layout/AppLayout";
import Table from "../components/Table";

import { api } from "../services/api";
import { useAuth } from "../hooks/useAuth";
import { usePaginatedTable } from "../hooks/usePaginatedTable";

export default function Publishers({ title }) {
	const navigate = useNavigate();
	const { user } = useAuth();

	const canCreate =
		user?.roles?.includes("ADMIN") ||
		user?.roles?.includes("SUPER_ADMIN");

	const canEdit = canCreate;

	const canDelete =
		user?.roles?.includes("SUPER_ADMIN");

	const fetchPublishers = useCallback(
		async ({ page, size, search }) => {
			const params = new URLSearchParams({
				page: String(page),
				size: String(size),
			});

			if (search) {
				params.set("search", search);
			}

			const response = await api(
				`/publishers?${params.toString()}`
			);

			if (!response.ok) {
				const body = await response
					.json()
					.catch(() => null);

				throw new Error(
					body?.general ||
					body?.message ||
					"Error when loading publishers"
				);
			}

			return response.json();
		},
		[]
	);

	const {
		data: publishers,
		setData: setPublishers,

		loading,
		error,

		page,
		totalPages,

		search,
		setSearch,

		setPage,
	} = usePaginatedTable(fetchPublishers);

	function handleEdit(publisher) {
		navigate(`/publishers/${publisher.id}/edit`);
	}

	async function handleDelete(publisher) {
		const confirmed = window.confirm(
			`Delete publisher "${publisher.title}"?`
		);

		if (!confirmed) {
			return;
		}

		try {
			const response = await api(
				`/publishers/${publisher.id}`,
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
					"Error when deleting publisher"
				);
			}

			toast.success(
				"Publisher deleted successfully"
			);

			if (publishers.length === 1 && page > 0) {
				setPage(page - 1);
			} else {
				setPublishers(currentPublishers =>
					currentPublishers.filter(
						currentPublisher =>
							currentPublisher.id !== publisher.id
					)
				);
			}
		} catch (err) {
			toast.error(
				err.message ||
					"Error when deleting publisher"
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
			render: publisher =>
				publisher.description ?? "-",
		},
		{
			key: "actions",
			label: "Actions",
			render: publisher => (
				<div className="d-flex gap-2">
					{canEdit && (
						<button
							type="button"
							className="btn btn-sm btn-primary"
							onClick={() =>
								handleEdit(publisher)
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
								handleDelete(publisher)
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
					Publishers List
				</h2>

				{canCreate && (
					<button
						type="button"
						className="btn btn-primary"
						onClick={() =>
							navigate("/publishers/create")
						}
					>
						Create Publisher
					</button>
				)}
			</div>

			<Table
				columns={columns}
				data={publishers}
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