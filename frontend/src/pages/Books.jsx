import { useEffect, useState } from "react";
import AppLayout from "../components/layout/AppLayout";
import Table from "../components/Table";
import { api } from "../services/api";

export default function Books({ title }) {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token){
      return
    }

    async function fetchBooks() {
      try {
        const res = await api("/books");
        const data = await res.json();

        setBooks(data);
      } catch (err) {
        setError("Error when loading books");
      } finally {
        setLoading(false);
      }
    }

    fetchBooks();
  }, []);

  const columns = [
    { key: "id", label: "ID", accessor: "id" },
    {
      key: "authors",
      label: "Authors",
      render: (book) => book.authors
    },
    {
      key: "title",
      label: "Title",
      render: (book) => book.title
    },
    {key:"isbn", label: "ISBN", render: (book) => book.isbn},
    {
      key: "categories",
      label: "Categories",
      render: (book) => book.categories
    },
    {
      key: "year",
      label: "Year",
      render: (book) => book.publicationYear
    },
    {
      key: "actions",
      label: "Actions",
      render: (user) => (
        <div style={{ display: "flex", gap: "8px" }}>
          <button
            onClick={() => handleEdit(user)}
            style={actionButton("#3b82f6")}
          >
            Edit
          </button>

          <button
            onClick={() => handleDelete(user)}
            style={actionButton("#ef4444")}
          >
            Delete
          </button>
        </div>
      )
    }
  ]

  function handleEdit(book) {
    console.log("Editbook:", book);
    // depois: navigate(`/books/${book.id}`)
  }

  async function handleDelete(book) {
    const confirmDelete = confirm(`Deletar ${book.title}?`);

    if (!confirmDelete) return;

    try {
      await api(`/books/${book.id}`, {
        method: "DELETE"
      });

      setBooks((prev) => prev.filter((u) => u.id !== book.id));
    } catch (err) {
      alert("Error when deleting book");
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
      <h2 style={{ marginBottom: "20px" }}>Book List</h2>

      {loading && <p>Loading...</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}
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
        Create Book
      </button>
      {!loading && !error && (
        <Table columns={columns} data={books} />
      )}
    </AppLayout>
  );
}