import { ENV } from "../config/env";

const BASE_URL = ENV.API_URL;

export async function api(path, options = {}) {
  const token = localStorage.getItem("token");

  const response = await fetch(`${BASE_URL}${path}`, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...(token && { Authorization: `Bearer ${token}` }),
      ...options.headers,
    },
  });

  const isPublicRequest =
    path === "/auth/login" ||
    path === "/auth/signup";

  if (response.status === 401 && !isPublicRequest) {
    const body = await response
      .clone()
      .json()
      .catch(() => null);

    const tokenIsInvalid =
      body?.code === "TOKEN_EXPIRED" ||
      body?.code === "INVALID_TOKEN" ||
      body?.code === "TOKEN_MISSING";

    if (tokenIsInvalid) {
      console.warn("Invalid or expired token, logging out...");

      localStorage.removeItem("token");
      window.location.href = "/";

      return response;
    }
  }

  return response;
}