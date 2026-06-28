import { ENV } from "../config/env";

const BASE_URL = ENV.API_URL;

export async function api(path, options = {}) {
  const token = localStorage.getItem("token");

  const response = await fetch(`${BASE_URL}${path}`, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...(token && { Authorization: `Bearer ${token}` }),
      ...options.headers
    }
  });

  const isLoginRequest = path === "/auth/login";

  if (response.status === 401 && !isLoginRequest) {
    console.warn("Invalid or expired token, logging out...");

    localStorage.removeItem("token");

    window.location.href = "/login";

    return;
  }

  return response;
}