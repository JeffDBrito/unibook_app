const API_URL = "http://localhost:8080";

export async function getUsers() {
  const response = await fetch(`${API_URL}/users`);
  return response.json();
}

export async function createUser(user) {
  const response = await fetch(`${API_URL}/users`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(user)
  });

  return response.json();
}