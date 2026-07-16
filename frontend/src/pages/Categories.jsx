import { useEffect, useState } from "react";
import AppLayout from "../components/layout/AppLayout";
import Table from "../components/Table";
import { api } from "../services/api";
import { useAuth } from "../hooks/useAuth";

export default function Categories({ title }) {
	const [categories, setCategories] = useState([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState("");
	// import { useAuth } from "../hooks/useAuth";
	const { token, user } = useAuth();

	const canEdit = user?.roles?.includes("ADMIN") ||
		user?.roles?.includes("SUPER_ADMIN");

	const canCreate =
		user?.roles?.includes("ADMIN") ||
		user?.roles?.includes("SUPER_ADMIN");

	const canDelete =
		user?.roles?.includes("SUPER_ADMIN");

	useEffect(() => {
		const token = localStorage.getItem("token");

		if (!token) {
			return
		}

		async function fetchEntity() {
			try {
				const res = await api("/categories");
				const data = await res.json();

				setCategories(data);
			} catch (err) {
				setError("Error when loading categories");
			} finally {
				setLoading(false);
			}
		}

		fetchEntity();
	}, []);

	const columns = [
		{
			key: "title",
			label: "Title",
			render: (category) => category.title
		},
		{
			key: "description",
			label: "Description",
			render: (category) => category.description
		},
		{
			key: "actions",
			label: "Actions",
			render: (category) => (
				<div style={{ display: "flex", gap: "8px" }}>
					{
						user?.roles?.includes("SUPER_ADMIN") || user?.roles?.includes("ADMIN") ?
							<button
								onClick={() => handleEdit(category)}
								style={actionButton("#3b82f6")}
							>
								Edit
							</button>
							: ""
					}

					{
						user?.roles?.includes("SUPER_ADMIN") ?
							<button
								onClick={() => handleDelete(category)}
								style={actionButton("#ef4444")}
							>
								Delete
							</button>
							: ""
					}
				</div>
			)
		}
	]

	function handleEdit(category) {
		console.log("Editcategory:", category);
		// depois: navigate(`/categories/${category.id}`)
	}

	async function handleDelete(category) {
		const confirmDelete = confirm(`Deletar ${category.title}?`);

		if (!confirmDelete) return;

		try {
			await api(`/categories/${category.id}`, {
				method: "DELETE"
			});

			setCategories((prev) => prev.filter((u) => u.id !== category.id));
		} catch (err) {
			alert("Error when deleting category");
		}
	}

	function actionButton(color) {
		return {
			padding: "6px 10px",
			border: "none",
			borderRadius: "4px",
			background: color,
			color: "#fff",
			cursor: "pointer",
			fontSize: "12px"
		};
	}

	return (
		<AppLayout title={title}>
			<h2 style={{ marginBottom: "20px" }}>Categories List</h2>

			{loading && <p>Loading...</p>}
			{error && <p style={{ color: "red" }}>{error}</p>}
			{
				canCreate && (
					<button
						style={{
							marginBottom: "10px",
							padding: "8px 12px",
							background: "#4f46e5",
							color: "#fff",
							border: "none",
							borderRadius: "4px"
						}}
					>
						Create Category
					</button>
				)
			}

			{!loading && !error && (
				<Table columns={columns} data={categories} />
			)}
		</AppLayout>
	);
}