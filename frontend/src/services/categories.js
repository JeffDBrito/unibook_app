import { api } from "./api";

export async function getCategories() {
  const response = await api("/categories");

  if (!response.ok) {
    throw new Error("Error loading categories");
  }

  return response.json();
}