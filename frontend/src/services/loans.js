import { api } from "./api";

export async function getLoans() {
  const response = await api("/loans");

  if (!response.ok) {
    throw new Error("Error loading loans");
  }

  return response.json();
}