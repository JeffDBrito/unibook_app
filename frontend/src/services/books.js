import { api } from "./api";

export async function getBooks({ page = 0, size = 10, search = "" } = {}) {
    const params = new URLSearchParams({
        page,
        size,
    });
    
    if (search && typeof search === 'string'){
        if (search.trim()) {
            params.append("search", search);
        }
    }

    const response = await api(`/books?${params.toString()}`);

    if (!response.ok) {
        throw await response.json();
    }

    return response.json();
}

export async function findBookById(id){
    const response = await api(`/books/${id}`, {
        method: "GET"
    });

    if (!response.ok) {
        throw await response.json();
    }

    return response.json();
}

export async function createBook(data) {
    const response = await api("/books", {
        method: "POST",
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        throw await response.json();
    }

    return response.json();
}

export async function editBook(id, data) {
    const response = await api(`/books/${id}`, {
        method: "PATCH",
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        throw await response.json();
    }

    return response.json();
}
