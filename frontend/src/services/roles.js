import { api } from "./api";

export async function getRoles() {
  const response = await api("/roles");

  if (!response.ok) {
    throw new Error("Error loading roles");
  }

  return response.json();
}