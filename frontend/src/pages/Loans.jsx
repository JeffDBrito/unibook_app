import { useEffect, useState } from "react";
import AppLayout from "../components/layout/AppLayout";
import Table from "../components/Table";
import { api } from "../services/api";
import { useAuth } from "../hooks/useAuth";


export default function Loans({ title }) {
	const [loans, setLoans] = useState([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState("");
	const { token, user } = useAuth();

	const canEdit = user?.roles?.includes("ADMIN") ||
		user?.roles?.includes("SUPER_ADMIN");

	const canCreate =
		user?.roles?.includes("ADMIN") ||
		user?.roles?.includes("SUPER_ADMIN");

	const canDelete =
		user?.roles?.includes("ADMIN") ||
		user?.roles?.includes("SUPER_ADMIN");


	useEffect(() => {
		const token = localStorage.getItem("token");

		if (!token) {
			return
		}

		async function fetchEntity() {
			try {
				const res = await api("/loan");
				const data = await res.json();

				setLoans(data);
			} catch (err) {
				setError("Error when loading loans");
			} finally {
				setLoading(false);
			}
		}

		fetchEntity();
	}, []);

	const columns = [
		{
			key: "copy_code",
			label: "Copy code",
			render: (loan) => loan.copy.code
		},
		{
			key: "title",
			label: "Title",
			render: (loan) => loan.copy.book.title
		},
		{
			key: "user",
			label: "User",
			render: (loan) => loan.user.person.name
		},
		{
			key: "loan_date",
			label: "Loan Date",
			render: (loan) => loan.loanDate
		},
		{
			key: "due_date",
			label: "Due Date",
			render: (loan) => loan.dueDate
		},
		{
			key: "return_date",
			label: "Return Date",
			render: (loan) => loan.returnDate ? loan.returnDate : "Pending"
		},
		{
			key: "status",
			label: "Status",
			render: (loan) => loan.status
		},
		{
			key: "actions",
			label: "Actions",
			render: (loan) => (
				<div style={{ display: "flex", gap: "8px" }}>
					{
						user?.roles?.includes("SUPER_ADMIN") || user?.roles?.includes("ADMIN") ?
							<button
								onClick={() => handleEdit(loan)}
								style={actionButton("#3b82f6")}
							>
								Edit
							</button>
							: ""
					}

					{
						user?.roles?.includes("SUPER_ADMIN") ?
							<button
								onClick={() => handleDelete(loan)}
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

	function handleEdit(loan) {
		console.log("Editloan:", loan);
		// depois: navigate(`/loans/${loan.id}`)
	}

	async function handleDelete(loan) {
		const confirmDelete = confirm(`Deletar ${loan.title}?`);

		if (!confirmDelete) return;

		try {
			await api(`/loans/${loan.id}`, {
				method: "DELETE"
			});

			setLoans((prev) => prev.filter((u) => u.id !== loan.id));
		} catch (err) {
			alert("Error when deleting loan");
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
			<h2 style={{ marginBottom: "20px" }}>Loans List</h2>

			{loading && <p>Loading...</p>}
			{error && <p style={{ color: "red" }}>{error}</p>}
			{canCreate && (
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
					Create Loan
				</button>)
			}
			{!loading && !error && (
				<Table columns={columns} data={loans} />
			)}
		</AppLayout>
	);
}