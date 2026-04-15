import { api } from "./api";

export async function loginRequest(login, password) {
  const response = await api("/auth/login", {
    method: "POST",
    body: JSON.stringify({ login, password })
  });

  if (!response.ok) {
    throw new Error("Invalid Login or Password");
  }

  return response.json();
}