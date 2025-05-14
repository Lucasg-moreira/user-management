"use client";

import { useState, useCallback, useEffect } from "react";
import { useRouter } from "next/navigation";
import { Filters } from "./components/filters";
import { Table } from "./components/table";
import { api } from "@/utils/api";

const apiUrl = process.env.NEXT_PUBLIC_API_URL;

export default function PeoplePage() {
  const router = useRouter();

  const [clients, setClients] = useState([]);

  const [filters, setFilters] = useState();

  useEffect(() => {
    async function fetchData() {
      const response = await api.get(`${apiUrl}/client`)
      setClients(response.content)
    }

    fetchData()
  }, [])

  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(10);

  const handleFilter = useCallback((filters) => {
    setFilters(filters)
  }, []);

  useEffect(() => {
    async function getWithFilters() {
      if (!filters)
        return

      const response = await api.get(`${apiUrl}/client/filter?name=${filters.name ? filters.name : ''}&createdAt=${filters.createdAt ? filters.createdAt : ''}&cpfCnpj=${filters.cpfCnpj ? filters.cpfCnpj : ''}`)

      if (response) {
        setClients(response.content)
      }
    }

    getWithFilters()
  }, [filters])

  const handleEdit = useCallback((person) => {
    router.push(`pessoas/${person.id}/edit`)
  }, []);

  const handleDelete = useCallback((id) => {
    async function deleteClient() {
      const response = await api.delete(`${apiUrl}/client/${id}`)

      if (response)
        window.location.reload()
    }

    deleteClient()

  }, []);

  const handleLogout = useCallback(() => {

    async function logout() {
      await api.get(`${apiUrl}/auth/logout`)

      sessionStorage.clear()

      router.push('/login')
    }

    logout()

  }, [])

  const handlePageChange = useCallback((page) => {
    setCurrentPage(page);
  }, []);

  const handleItemsPerPageChange = useCallback((newItemsPerPage) => {
    setItemsPerPage(newItemsPerPage);
    setCurrentPage(1);
  }, []);

  return (
    <div className="min-h-screen bg-gray-100 dark:bg-gray-900 py-8 px-4 sm:px-6 lg:px-8">
      <div className="max-w-7xl mx-auto">
        <div className="flex justify-between items-center mb-6">
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white">
            Gerenciamento de Pessoas
          </h1>

          <div>
            <button
              onClick={() => router.push('/pessoas/nova')}
              className="px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 transition-colors duration-200"
            >
              Nova Pessoa
            </button>

            <button
              onClick={handleLogout}
              className="ml-4 px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 transition-colors duration-200"
            >
              Logout
            </button>

          </div>

        </div>

        <Filters onFilter={handleFilter} />

        <Table
          people={clients}
          onEdit={handleEdit}
          onDelete={handleDelete}
          currentPage={currentPage}
          itemsPerPage={itemsPerPage}
          totalItems={clients.length}
          onPageChange={handlePageChange}
          onItemsPerPageChange={handleItemsPerPageChange}
        />
      </div>
    </div>
  );
} 