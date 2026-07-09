import { api } from "./api";

export async function getUser(id) {
  const response = await api(`/users/${id}`);

  if (!response.ok) {
    throw new Error("Error loading user");
  }

  return response.json();
}

export async function updateUser(id, data) {
  const response = await api(`/users/${id}`, {
    method: "PATCH",
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    throw await response.json();
  }

  return response.json();
}

export async function createUser(data) {
  const response = await api("/users", {
    method: "POST",
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    throw await response.json();
  }

  return response.json();
}