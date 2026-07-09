import { useEffect, useState } from "react";
import AppLayout from "../components/layout/AppLayout";
import Table from "../components/Table";
import { api } from "../services/api";
import { useAuth } from "../hooks/useAuth";

export default function Copies({ title }) {
  const [copies, setCopies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const { token, user } = useAuth()

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token){
      return
    }

    async function fetchEntity() {
      try {
        const res = await api("/copy");
        const data = await res.json();

        setCopies(data);
      } catch (err) {
        setError("Error when loading copies");
      } finally {
        setLoading(false);
      }
    }

    fetchEntity();
  }, []);

  const columns = [
    { key: "id", label: "ID", accessor: "id" },
    {
      key: "code",
      label: "Code",
      render: (copy) => copy.code
    },
    {
      key: "title",
      label: "Title",
      render: (copy) => copy.book.title
    },
    {
      key: "authors",
      label: "Authors",
      render: (copy) => copy.book.authors
    },
    {key:"isbn", label: "ISBN", render: (copy) => copy.book.isbn},
    {
      key: "inventory",
      label: "Inventory",
      render: (copy) => copy.inventoryAddress
    },
    {
      key: "actions",
      label: "Actions",
      render: (copy) => (
        <div style={{ display: "flex", gap: "8px" }}>
          {
            user?.roles?.includes("SUPER_ADMIN") || user?.roles?.includes("ADMIN") ? 
              <button
                onClick={() => handleEdit(copy)}
                style={actionButton("#3b82f6")}
              >
                Edit
              </button>
            : ""
          }

          {
            user?.roles?.includes("SUPER_ADMIN") ?
              <button
                onClick={() => handleDelete(copy)}
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

  function handleEdit(copy) {
    console.log("Editcopy:", copy);
    // depois: navigate(`/copies/${copy.id}`)
  }

  async function handleDelete(copy) {
    const confirmDelete = confirm(`Deletar ${copy.title}?`);

    if (!confirmDelete) return;

    try {
      await api(`/copies/${copy.id}`, {
        method: "DELETE"
      });

      setCopies((prev) => prev.filter((u) => u.id !== copy.id));
    } catch (err) {
      alert("Error when deleting copy");
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
      <h2 style={{ marginBottom: "20px" }}>Copies List</h2>

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
        Create Copy
      </button>
      {!loading && !error && (
        <Table columns={columns} data={copies} />
      )}
    </AppLayout>
  );
}