import { useCallback, useEffect, useState } from "react";

export function usePaginatedTable(
  fetchData,
  {
    initialPage = 0,
    pageSize = 10,
    debounceDelay = 400,
    initialSearch = "",
  } = {}
) {
  const [data, setData] = useState([]);
  const [page, setPage] = useState(initialPage);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  const [search, setSearch] = useState(initialSearch);
  const [debouncedSearch, setDebouncedSearch] = useState(initialSearch);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const timeout = setTimeout(() => {
      setDebouncedSearch(search.trim());
      setPage(0);
    }, debounceDelay);

    return () => clearTimeout(timeout);
  }, [search, debounceDelay]);

  const loadData = useCallback(async () => {
    setLoading(true);
    setError("");

    try {
      const response = await fetchData({
        page,
        size: pageSize,
        search: debouncedSearch,
      });

      setData(response.content ?? []);
      setTotalPages(response.totalPages ?? 0);
      setTotalElements(response.totalElements ?? 0);
    } catch (err) {
      setData([]);
      setTotalPages(0);
      setTotalElements(0);

      setError(
        err?.message ||
        err?.general ||
        "Error when loading data"
      );
    } finally {
      setLoading(false);
    }
  }, [fetchData, page, pageSize, debouncedSearch]);

  useEffect(() => {
    loadData();
  }, [loadData]);

  function changeSearch(value) {
    setSearch(value);
  }

  function changePage(nextPage) {
    if (nextPage < 0 || nextPage >= totalPages) {
      return;
    }

    setPage(nextPage);
  }

  function removeItemFromCurrentPage(id) {
    setData(currentData =>
      currentData.filter(item => item.id !== id)
    );
  }

  return {
    data,
    setData,

    page,
    setPage: changePage,
    pageSize,
    totalPages,
    totalElements,

    search,
    setSearch: changeSearch,
    debouncedSearch,

    loading,
    error,

    reload: loadData,
    removeItemFromCurrentPage,
  };
}