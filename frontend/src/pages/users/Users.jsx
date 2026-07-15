import { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import AppLayout from "../../components/layout/AppLayout";
import Table from "../../components/Table";

import { api } from "../../services/api";
import { useAuth } from "../../hooks/useAuth";

const PAGE_SIZE = 10;
const SEARCH_DELAY = 400;

export default function Users({ title }) {
	const navigate = useNavigate();
	const { user } = useAuth();

	const [users, setUsers] = useState([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState("");

	const [page, setPage] = useState(0);
	const [totalPages, setTotalPages] = useState(0);

	const [search, setSearch] = useState("");
	const [debouncedSearch, setDebouncedSearch] = useState("");

	const canEdit =
		user?.roles?.includes("ADMIN") ||
		user?.roles?.includes("SUPER_ADMIN");

	const canDelete = user?.roles?.includes("SUPER_ADMIN");

	const fetchUsers = useCallback(async () => {
		setLoading(true);
		setError("");

		try {
			const params = new URLSearchParams({
				page: String(page),
				size: String(PAGE_SIZE),
			});

			if (debouncedSearch.trim()) {
				params.set("search", debouncedSearch.trim());
			}

			const response = await api(`/users?${params.toString()}`);

			if (!response.ok) {
				const body = await response.json().catch(() => null);

				throw new Error(
					body?.general ||
					body?.message ||
					"Error when loading users"
				);
			}

			const data = await response.json();

			setUsers(data.content ?? []);
			setTotalPages(data.totalPages ?? 0);
		} catch (err) {
			setUsers([]);
			setError(err.message || "Error when loading users");
		} finally {
			setLoading(false);
		}
	}, [page, debouncedSearch]);

	useEffect(() => {
		const timeout = setTimeout(() => {
			setDebouncedSearch(search);
			setPage(0);
		}, SEARCH_DELAY);

		return () => clearTimeout(timeout);
	}, [search]);

	useEffect(() => {
		fetchUsers();
	}, [fetchUsers]);

	function handleEdit(selectedUser) {
		navigate(`/users/${selectedUser.id}/edit`);
	}

	function handleCreateUser() {
		navigate("/users/create");
	}

	async function handleDelete(selectedUser) {
		// Depois substitua este confirm por um modal.
		const confirmed = window.confirm(
			`Delete user ${selectedUser.login}?`
		);

		if (!confirmed) {
			return;
		}

		try {
			const response = await api(`/users/${selectedUser.id}`, {
				method: "DELETE",
			});

			if (!response.ok) {
				const body = await response.json().catch(() => null);

				throw new Error(
					body?.general ||
					body?.message ||
					"Error when deleting user"
				);
			}

			toast.success("User deleted successfully");

			if (users.length === 1 && page > 0) {
				setPage((currentPage) => currentPage - 1);
			} else {
				await fetchUsers();
			}
		} catch (err) {
			toast.error(err.message || "Error when deleting user");
		}
	}

	const columns = [
		{
			key: "id",
			label: "ID",
			accessor: "id",
		},
		{
			key: "login",
			label: "Login",
			accessor: "login",
		},
		{
			key: "name",
			label: "Name",
			render: (currentUser) => currentUser.person?.name ?? "-",
		},
		{
			key: "email",
			label: "Email",
			render: (currentUser) => currentUser.person?.email ?? "-",
		},
		{
			key: "roles",
			label: "Roles",
			render: (currentUser) =>
				Array.isArray(currentUser.roles)
					? currentUser.roles
						.map((role) =>
							typeof role === "string" ? role : role.name
						)
						.join(", ")
					: currentUser.roles ?? "-",
		},
		{
			key: "actions",
			label: "Actions",
			render: (currentUser) => (
				<div className="d-flex gap-2">
					{canEdit && (
						<button
							type="button"
							className="btn btn-sm btn-primary"
							onClick={() => handleEdit(currentUser)}
						>
							Edit
						</button>
					)}

					{canDelete && (
						<button
							type="button"
							className="btn btn-sm btn-danger"
							onClick={() => handleDelete(currentUser)}
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
				<h2 className="mb-0">User List</h2>

				{canEdit && (
					<button
						type="button"
						className="btn btn-primary"
						onClick={handleCreateUser}
					>
						Create User
					</button>
				)}
			</div>

			<Table
				columns={columns}
				data={users}
				loading={loading}
				error={error}
				search={search}
				onSearchChange={setSearch}
				searchPlaceholder="Search by name, email or login..."
				page={page}
				totalPages={totalPages}
				onPageChange={setPage}
			/>
		</AppLayout>
	);
}