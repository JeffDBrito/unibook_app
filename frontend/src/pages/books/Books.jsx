import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { requestLoan } from "../../services/loanRequests";
import { toast } from "react-toastify";

import AppLayout from "../../components/layout/AppLayout";
import Table from "../../components/Table";

import { api } from "../../services/api";
import { useAuth } from "../../hooks/useAuth";

const PAGE_SIZE = 10;
const SEARCH_DELAY = 400;

export default function Books({ title }) {

	// Constants and hooks
	const navigate = useNavigate();
	const { user } = useAuth();

	const [books, setBooks] = useState([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState("");

	const [page, setPage] = useState(0);
	const [totalPages, setTotalPages] = useState(0);

	const [search, setSearch] = useState("");
	const [debouncedSearch, setDebouncedSearch] = useState("");

	// Permissions based on user roles
	const canCreate =
		user?.roles?.includes("ADMIN") ||
		user?.roles?.includes("SUPER_ADMIN");

	const canEdit = canCreate;

	const canDelete = user?.roles?.includes("SUPER_ADMIN");

	const canRent =
		user?.roles?.includes("LIBRARIAN") ||
		user?.roles?.includes("TEACHER") ||
		user?.roles?.includes("STUDENT");

	const canRequestLoan =
		user?.roles?.includes("STUDENT") ||
		user?.roles?.includes("TEACHER") ||
		user?.roles?.includes("LIBRARIAN");

	// Fetch books when page or debounced search changes
	useEffect(() => {
		const timeout = setTimeout(() => {
			setDebouncedSearch(search.trim());
			setPage(0);
		}, SEARCH_DELAY);

		return () => clearTimeout(timeout);
	}, [search]);

	useEffect(() => {
		async function fetchBooks() {
			setLoading(true);
			setError("");

			try {
				const params = new URLSearchParams({
					page: String(page),
					size: String(PAGE_SIZE),
				});

				if (debouncedSearch) {
					params.set("search", debouncedSearch);
				}

				const response = await api(
					`/books?${params.toString()}`
				);

				if (!response.ok) {
					const body = await response.json().catch(() => null);

					throw new Error(
						body?.general ||
						body?.message ||
						"Error when loading books"
					);
				}

				const data = await response.json();

				setBooks(data.content ?? []);
				setTotalPages(data.totalPages ?? 0);
			} catch (err) {
				setBooks([]);
				setTotalPages(0);
				setError(err.message || "Error when loading books");
			} finally {
				setLoading(false);
			}
		}

		fetchBooks();
	}, [page, debouncedSearch]);

	function handleEdit(book) {
		navigate(`/books/${book.id}/edit`)
	}

	async function handleDelete(book) {
		const confirmed = window.confirm(
			`Delete ${book.title}?`
		);

		if (!confirmed) {
			return;
		}

		try {
			const response = await api(`/books/${book.id}`, {
				method: "DELETE",
			});

			if (!response.ok) {
				const body = await response.json().catch(() => null);

				throw new Error(
					body?.general ||
					body?.message ||
					"Error when deleting book"
				);
			}

			toast.success("Book deleted successfully");

			if (books.length === 1 && page > 0) {
				setPage(currentPage => currentPage - 1);
			} else {
				setBooks(currentBooks =>
					currentBooks.filter(
						currentBook => currentBook.id !== book.id
					)
				);
			}
		} catch (err) {
			toast.error(err.message || "Error when deleting book");
		}
	}

	async function handleRequestLoan(book) {
		try {
			await requestLoan(book.id);
			toast.success(
				"Loan request sent successfully."
			);
			book.requestedByCurrentUser = true;
		} catch (err) {
			toast.error(
				err.bookId ??
				err.message ??
				"Unable to request this book."
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
			key: "isbn",
			label: "ISBN",
			accessor: "isbn",
		},
		{
			key: "publisher",
			label: "Publisher",
			render: book =>
				book.publisher?.name ??
				book.publisher?.title ??
				book.publisher ??
				"-",
		},
		{
			key: "authors",
			label: "Authors",
			render: book =>
				Array.isArray(book.authors)
					? book.authors
						.map(author =>
							typeof author === "string"
								? author
								: author.name
						)
						.join(", ")
					: book.authors ?? "-",
		},
		{
			key: "categories",
			label: "Categories",
			render: book =>
				Array.isArray(book.categories)
					? book.categories
						.map(category =>
							typeof category === "string"
								? category
								: category.title
						)
						.join(", ")
					: book.categories ?? "-",
		},
		{
			key: "actions",
			label: "Actions",
			render: book => (
				<div className="d-flex gap-2">
					{canEdit && (
						<button
							className="btn btn-sm btn-primary"
							onClick={() => handleEdit(book)}
						>
							Edit
						</button>
					)}
					{canDelete && (
						<button
							className="btn btn-sm btn-danger"
							onClick={() => handleDelete(book)}
						>
							Delete
						</button>
					)}
					{canRequestLoan && (
						<button
							className="btn btn-sm btn-success"
							disabled={book.requestedByCurrentUser}
							onClick={() => handleRequestLoan(book)}
						>
							{book.requestedByCurrentUser
								? "Requested"
								: "Request"}
						</button>
					)}
				</div>
			)
		},
	];

	return (
		<AppLayout title={title}>
			<div className="d-flex justify-content-between align-items-center mb-3">
				<h2 className="mb-0">Book List</h2>

				{canCreate && (
					<button
						type="button"
						className="btn btn-primary"
						onClick={() => navigate("/books/create")}
					>
						Create Book
					</button>
				)}
			</div>

			<Table
				columns={columns}
				data={books}
				loading={loading}
				error={error}
				search={search}
				onSearchChange={setSearch}
				searchPlaceholder="Search by title or ISBN..."
				page={page}
				totalPages={totalPages}
				onPageChange={setPage}
			/>
		</AppLayout>
	);
}