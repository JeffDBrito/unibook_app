import { useState } from "react";
import { useAuth } from "../hooks/useAuth";
import { loginRequest } from "../services/auth";
import { useNavigate } from "react-router-dom";

import Input from "../components/Input";
import Button from "../components/Button";

export default function Login() {
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const { login: doLogin } = useAuth();
  const navigate = useNavigate();

  async function handleLogin(e) {
    e.preventDefault();

    setLoading(true);

    try {
      const data = await loginRequest(login, password);
      doLogin(data.token);
      navigate("/dashboard");
    } catch (err) {
      setError("Invalid credentials");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div style={{ maxWidth: 300, margin: "100px auto" }}>
      <h2>Login</h2>

      <form onSubmit={handleLogin}>
        <Input
          type="text"
          placeholder="Login"
          value={login}
          onChange={(e) => setLogin(e.target.value)}
        />

        <Input
          type="password"
          placeholder="Senha"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <Button type="submit">
          {loading ? "Entrando..." : "Entrar"}
        </Button>
      </form>

      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
}