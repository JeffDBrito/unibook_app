import { useState, useEffect } from "react";
import { useAuth } from "../hooks/useAuth";
import { loginRequest } from "../services/auth";
import { Link, useNavigate, useLocation } from "react-router-dom";

export default function Login() {
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const { login: doLogin } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  const [accountCreated, setAccountCreated] = useState(
    location.state?.accountCreated || false
  );
  
  useEffect(() => {
    if (location.state?.success) {
      navigate(location.pathname, {
        replace: true,
        state: {}
      });
    }
  }, []);

  async function handleLogin(e) {
    e.preventDefault();

    setLoading(true);
    setError("");

    try {
      localStorage.removeItem("token");

      const data = await loginRequest(login, password);

      localStorage.setItem("token", data.token);

      doLogin(data.token);

      navigate("/dashboard");
    } catch (err) {
      setError("Invalid credentials");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="loginPage container vh-100 d-flex align-items-center justify-content-center">
      <div
        className="card shadow-sm p-4"
        style={{ width: "100%", maxWidth: "420px" }}
      >
        <div className="text-center mb-4">
          <h2 className="fw-bold logo">UniBook</h2>
          <p className="text-muted mb-0">
            Login to access the system
          </p>
        </div>

        {accountCreated && (
          <div className="alert alert-success text-center">
            Account created successfully! <br />
            You can now log in.
          </div>
        )}

        {error && (
          <div className="alert alert-danger">
            {error}
          </div>
        )}

        <form onSubmit={handleLogin}>
          <div className="mb-3">
            <label className="form-label">
              Login
            </label>

            <input
              type="text"
              className="form-control"
              placeholder="Enter your login"
              value={login}
              onChange={(e) => setLogin(e.target.value)}
              required
            />
          </div>

          <div className="mb-4">
            <label className="form-label">
              Password
            </label>

            <input
              type="password"
              className="form-control"
              placeholder="Enter your password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <div className="my-3">
            <p className="text-center mt-3">
              Don't have an account?{" "}
              <Link to="/signup">
                  Create one
              </Link>
            </p>
          </div>

          <button
            type="submit"
            className="btn btn-primary w-100"
            disabled={loading}
          >
            {loading ? (
              <>
                <span
                  className="spinner-border spinner-border-sm me-2"
                  role="status"
                />
                Logging in...
              </>
            ) : (
              "Login"
            )}
          </button>
        </form>
      </div>
    </div>
  );
}