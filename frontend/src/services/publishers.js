import { api } from "./api";

export async function getPublishers() {
  const response = await api("/publishers");

  if (!response.ok) {
    throw new Error("Error loading publishers");
  }

  return response.json();
}