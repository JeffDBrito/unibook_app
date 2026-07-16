import { useEffect, useState } from "react";
import AppLayout from "../components/layout/AppLayout";
import Table from "../components/Table";
import { api } from "../services/api";
import { useAuth } from "../hooks/useAuth";


export default function Fines({ title }) {
	const [fines, setFines] = useState([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState("");
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
				const res = await api("/fines");
				const data = await res.json();

				setFines(data);
			} catch (err) {
				setError("Error when loading fines");
			} finally {
				setLoading(false);
			}
		}

		fetchEntity();
	}, []);

	const columns = [
		{
			key: "authors",
			label: "Authors",
			render: (fine) => fine.authors
		},
		{
			key: "title",
			label: "Title",
			render: (fine) => fine.title
		},
		{ key: "isbn", label: "ISBN", render: (fine) => fine.isbn },
		{
			key: "categories",
			label: "Categories",
			render: (fine) => fine.categories
		},
		{
			key: "year",
			label: "Year",
			render: (fine) => fine.publicationYear
		},
		{
			key: "actions",
			label: "Actions",
			render: (fine) => (
				<div style={{ display: "flex", gap: "8px" }}>
					{
						user?.roles?.includes("SUPER_ADMIN") || user?.roles?.includes("ADMIN") ?
							<button
								onClick={() => handleEdit(fine)}
								style={actionButton("#3b82f6")}
							>
								Edit
							</button>
							: ""
					}

					{
						user?.roles?.includes("SUPER_ADMIN") ?
							<button
								onClick={() => handleDelete(fine)}
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

	function handleEdit(fine) {
		console.log("Editfine:", fine);
		// depois: navigate(`/fines/${fine.id}`)
	}

	async function handleDelete(fine) {
		const confirmDelete = confirm(`Deletar ${fine.title}?`);

		if (!confirmDelete) return;

		try {
			await api(`/fines/${fine.id}`, {
				method: "DELETE"
			});

			setFines((prev) => prev.filter((u) => u.id !== fine.id));
		} catch (err) {
			alert("Error when deleting fine");
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
			<h2 style={{ marginBottom: "20px" }}>Fines List</h2>

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
						Create Fine
					</button>
				)
			}
			{!loading && !error && (
				<Table columns={columns} data={fines} />
			)}
		</AppLayout>
	);
}