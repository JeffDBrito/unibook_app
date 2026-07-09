import { api } from "./api";

export async function getAuthors() {
  const response = await api("/authors");

  if (!response.ok) {
    throw new Error("Error loading authors");
  }

  return response.json();
}