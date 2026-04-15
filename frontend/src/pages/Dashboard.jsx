import { useAuth } from "../hooks/useAuth";
import Input from "../components/Input";
import Button from "../components/Button";

export default function Dashboard() {
  const { logout } = useAuth();

  return (
    <div>
      <h1>Dashboard</h1>
      <div style={{ maxWidth: "300px", margin: "20px auto" }}>
        <Button onClick={logout}>Logout</Button>
      </div>
    </div>
  );
}