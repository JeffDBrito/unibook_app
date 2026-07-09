import { Navigate } from "react-router-dom";
import { useAuth } from "../hooks/useAuth";

export function PrivateRoute({ children, roles = [] }) {
  const { token, user } = useAuth();

  if (!token) {
    return <Navigate to="/" replace />;
  }

  if (roles.length > 0 && !roles.some(role => user?.roles?.includes(role))) {
    return <Navigate to="/dashboard" replace />;
  }

  return children;
}