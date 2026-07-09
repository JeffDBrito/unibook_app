import { api } from "./api";

export async function signupRequest(data) {
  const response = await api("/auth/signup", {
    method: "POST",
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    throw await response.json();
  }

  return response.json();
}