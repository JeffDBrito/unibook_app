import { api } from "./api";

export async function getFines() {
  const response = await api("/fines");

  if (!response.ok) {
    throw new Error("Error loading fines");
  }

  return response.json();
}