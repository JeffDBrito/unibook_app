import { api } from "./api";

export async function getBook(id) {
  const response = await api(`/books/${id}`);

  if (!response.ok) {
    throw new Error("Error loading book");
  }

  return response.json();
}

export async function createBook(data) {
    console.log("Creating book with data:", data); // Debugging line
    const response = await api("/books", {
        method: "POST",
        body: JSON.stringify(data),
    });
    console.log("Response from API:", response); // Debugging line

    if (!response.ok) {
        throw await response.json();
    }

    return response.json();
}

export async function editBook(id, data) {
    console.log("Editing book with ID:", id); // Debugging line
    const response = await api(`/books/${id}`, {
        method: "PATCH",
        body: JSON.stringify(data),
    });
    console.log("Response from API:", response); // Debugging line

    if (!response.ok) {
        throw await response.json();
    }

    return response.json();
}
