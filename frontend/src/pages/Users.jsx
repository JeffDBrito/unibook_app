import { useEffect, useState } from "react";
import AppLayout from "../components/layout/AppLayout";
import Table from "../components/Table";
import { api } from "../services/api";

export default function Users({ title }) {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token){
      return
    }

    async function fetchUsers() {
      try {
        const res = await api("/users");
        const data = await res.json();

        setUsers(data);
      } catch (err) {
        setError("Error when loading users");
      } finally {
        setLoading(false);
      }
    }

    fetchUsers();
  }, []);

  const columns = [
    { key: "id", label: "ID", accessor: "id" },
    { key: "login", label: "Login", accessor: "login" },
    {
      key: "name",
      label: "Name",
      render: (user) => user.name
    },
    {
      key: "email",
      label: "Email",
      render: (user) => user.email
    },
    {
      key: "role",
      label: "Role",
      render: (user) => user.role
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
  ];

  function handleEdit(user) {
    console.log("Edituser:", user);
    // depois: navigate(`/users/${user.id}`)
  }

  async function handleDelete(user) {
    const confirmDelete = confirm(`Deletar ${user.login}?`);

    if (!confirmDelete) return;

    try {
      await api(`/users/${user.id}`, {
        method: "DELETE"
      });

      setUsers((prev) => prev.filter((u) => u.id !== user.id));
    } catch (err) {
      alert("Error when deleting user");
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
      <h2 style={{ marginBottom: "20px" }}>User List</h2>

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
        Create User
      </button>
      {!loading && !error && (
        <Table columns={columns} data={users} />
      )}
    </AppLayout>
  );
}