import { useEffect, useState } from "react";
import AppLayout from "../components/layout/AppLayout";
import Table from "../components/Table";
import { api } from "../services/api";
import { useAuth } from "../hooks/useAuth";

export default function Publishers({ title }) {
  const [publishers, setPublishers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const { token, user } = useAuth();
  

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token){
      return
    }

    async function fetchEntity() {
      try {
        const res = await api("/publishers");
        const data = await res.json();

        setPublishers(data);
      } catch (err) {
        setError("Error when loading publishers");
      } finally {
        setLoading(false);
      }
    }

    fetchEntity();
  }, []);

  const columns = [
    { key: "id", label: "ID", accessor: "id" },
    {
      key: "title",
      label: "Title",
      render: (publisher) => publisher.title
    },
    {
      key: "description",
      label: "Description",
      render: (publisher) => publisher.description
    },
    {
      key: "actions",
      label: "Actions",
      render: (publisher) => (
        <div style={{ display: "flex", gap: "8px" }}>
          {
            user?.roles?.includes("SUPER_ADMIN") || user?.roles?.includes("ADMIN") ? 
              <button
                onClick={() => handleEdit(publisher)}
                style={actionButton("#3b82f6")}
              >
                Edit
              </button>
            : ""
          }

          {
            user?.roles?.includes("SUPER_ADMIN") ?
              <button
                onClick={() => handleDelete(publisher)}
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

  function handleEdit(publisher) {
    console.log("Editpublisher:", publisher);
    // depois: navigate(`/publishers/${publisher.id}`)
  }

  async function handleDelete(publisher) {
    const confirmDelete = confirm(`Deletar ${publisher.title}?`);

    if (!confirmDelete) return;

    try {
      await api(`/publishers/${publisher.id}`, {
        method: "DELETE"
      });

      setPublishers((prev) => prev.filter((u) => u.id !== publisher.id));
    } catch (err) {
      alert("Error when deleting publisher");
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
      <h2 style={{ marginBottom: "20px" }}>Publishers List</h2>

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
        Create Publisher
      </button>
      {!loading && !error && (
        <Table columns={columns} data={publishers} />
      )}
    </AppLayout>
  );
}