import { useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import AppLayout from "../components/layout/AppLayout";
import Table from "../components/Table";

import { api } from "../services/api";
import { useAuth } from "../hooks/useAuth";
import { usePaginatedTable } from "../hooks/usePaginatedTable";

export default function Copies({ title }) {
	const navigate = useNavigate();
	const { user } = useAuth();

	const canCreate =
		user?.roles?.includes("ADMIN") ||
		user?.roles?.includes("SUPER_ADMIN");

	const canEdit = canCreate;

	const canDelete =
		user?.roles?.includes("SUPER_ADMIN");

	const fetchCopies = useCallback(
		async ({ page, size, search }) => {
			const params = new URLSearchParams({
				page: String(page),
				size: String(size),
			});

			if (search) {
				params.set("search", search);
			}

			const response = await api(
				`/copy?${params.toString()}`
			);

			if (!response.ok) {
				const body = await response
					.json()
					.catch(() => null);

				throw new Error(
					body?.general ||
					body?.message ||
					"Error when loading copies"
				);
			}

			return response.json();
		},
		[]
	);

	const {
		data: copies,
		setData: setCopies,

		loading,
		error,

		page,
		totalPages,

		search,
		setSearch,

		setPage,
		reload,
	} = usePaginatedTable(fetchCopies);

	function handleEdit(copy) {
		navigate(`/copy/${copy.id}/edit`);
	}

	async function handleDelete(copy) {
		const confirmed = window.confirm(
			`Delete copy ${copy.code}?`
		);

		if (!confirmed) {
			return;
		}

		try {
			const response = await api(
				`/copy/${copy.id}`,
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
					"Error when deleting copy"
				);
			}

			toast.success(
				"Copy deleted successfully"
			);

			if (copies.length === 1 && page > 0) {
				setPage(page - 1);
			} else {
				setCopies(currentCopies =>
					currentCopies.filter(
						currentCopy =>
							currentCopy.id !== copy.id
					)
				);
			}
		} catch (err) {
			toast.error(
				err.message ||
				"Error when deleting copy"
			);
		}
	}

	const columns = [
		{
			key: "code",
			label: "Code",
			accessor: "code",
		},
		{
			key: "title",
			label: "Title",
			render: copy =>
				copy.book?.title ?? "-",
		},
		{
			key: "isbn",
			label: "ISBN",
			render: copy =>
				copy.book?.isbn ?? "-",
		},
		{
			key: "section",
			label: "Section",
			render: copy =>
				copy.inventoryAddress ?? copy.status,
		},
		{
			key: "status",
			label: "Status",
			render: copy =>
				copy.status ?? "-",
		},
		{
			key: "actions",
			label: "Actions",
			render: copy => (
				<div className="d-flex gap-2">
					{canEdit && (
						<button
							type="button"
							className="btn btn-sm btn-primary"
							onClick={() => handleEdit(copy)}
						>
							Edit
						</button>
					)}

					{canDelete && (
						<button
							type="button"
							className="btn btn-sm btn-danger"
							onClick={() => handleDelete(copy)}
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
					Copies List
				</h2>

				{canCreate && (
					<button
						type="button"
						className="btn btn-primary"
						onClick={() =>
							navigate("/copy/create")
						}
					>
						Create Copy
					</button>
				)}
			</div>

			<Table
				columns={columns}
				data={copies}
				loading={loading}
				error={error}
				search={search}
				onSearchChange={setSearch}
				searchPlaceholder="Search by code"
				page={page}
				totalPages={totalPages}
				onPageChange={setPage}
			/>
		</AppLayout>
	);
}