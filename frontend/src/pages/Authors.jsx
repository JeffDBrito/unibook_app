import { useEffect, useState } from "react";
import AppLayout from "../components/layout/AppLayout";
import Table from "../components/Table";
import { api } from "../services/api";
import { useAuth } from "../hooks/useAuth";

export default function Authors({ title }) {
  const [authors, setAuthors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const { token, user } = useAuth();

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token){
      return
    }

    async function fetchAuthors() {
      try {
        const res = await api("/authors");
        const data = await res.json();

        setAuthors(data);
      } catch (err) {
        setError("Error when loading authors");
      } finally {
        setLoading(false);
      }
    }

    fetchAuthors();
  }, []);

  const columns = [
    { key: "id", label: "ID", accessor: "id" },
    {
      key: "person",
      label: "Name",
      render: (author) => author.person.name
    },
    {
      key: "bio",
      label: "Biography",
      render: (author) => author.biography      
    },
    {
      key: "actions",
      label: "Actions",
      render: (author) => (
        <div style={{ display: "flex", gap: "8px" }}>
          {
            user?.roles?.includes("SUPER_ADMIN") || user?.roles?.includes("ADMIN") ? 
              <button
                onClick={() => handleEdit(author)}
                style={actionButton("#3b82f6")}
              >
                Edit
              </button>
            : ""
          }

          {
            user?.roles?.includes("SUPER_ADMIN") ?
              <button
                onClick={() => handleDelete(author)}
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

  function handleEdit(author) {
    console.log("Editauthor:", author);
    // depois: navigate(`/authors/${author.id}`)
  }

  async function handleDelete(author) {
    const confirmDelete = confirm(`Deletar ${author.title}?`);

    if (!confirmDelete) return;

    try {
      await api(`/authors/${author.id}`, {
        method: "DELETE"
      });

      setAuthors((prev) => prev.filter((u) => u.id !== author.id));
    } catch (err) {
      alert("Error when deleting author");
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
      <h2 style={{ marginBottom: "20px" }}>Author List</h2>

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
        Create Author
      </button>
      {!loading && !error && (
        <Table columns={columns} data={authors} />
      )}
    </AppLayout>
  );
}