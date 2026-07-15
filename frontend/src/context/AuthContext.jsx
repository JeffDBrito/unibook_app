import { createContext, useState } from "react";

export const AuthContext = createContext();

function parseToken(token) {
  if (!token) return null;

  const payload = JSON.parse(atob(token.split(".")[1]));

  return {
    name: payload.name,
    login: payload.sub,
    roles: payload.roles || [],
    id: payload.id
  };
}

export function AuthProvider({ children }) {
  const savedToken = localStorage.getItem("token");

  const [token, setToken] = useState(savedToken);
  const [user, setUser] = useState(savedToken ? parseToken(savedToken) : null);

  function login(newToken) {
    localStorage.setItem("token", newToken);
    setToken(newToken);
    setUser(parseToken(newToken));
  }

  function logout() {
    localStorage.removeItem("token");
    setToken(null);
    setUser(null);
  }

  return (
    <AuthContext.Provider value={{ token, user, isAuthenticated: !!token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}