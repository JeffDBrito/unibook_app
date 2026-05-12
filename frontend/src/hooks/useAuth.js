import { useEffect, useState } from "react";

export function useAuth() {

  // Check token validity on mount
  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) return;

    try {
      const payload = JSON.parse(atob(token.split(".")[1]));

      const isExpired = payload.exp * 1000 < Date.now();

      if (isExpired) {
        localStorage.removeItem("token");
        window.location.href = "/";
      }
    } catch (e) {
      localStorage.removeItem("token");
    }
  }, []);

  const [token, setToken] = useState(localStorage.getItem("token"));

  function login(newToken) {
    localStorage.setItem("token", newToken);
    setToken(newToken);
  }

  function logout() {
    localStorage.removeItem("token");
    setToken(null);
  }

  return {
    token,
    isAuthenticated: !!token,
    login,
    logout
  };
}