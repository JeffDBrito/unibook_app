import { api } from "./api";

export async function signupRequest(data) {
  const response = await api("/auth/signup", {
    method: "POST",
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    const error = await response.json().catch(() => null);
    throw new Error(error?.message || "Error creating account");
  }

  return response.json();
}