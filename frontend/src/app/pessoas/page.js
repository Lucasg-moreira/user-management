"use client";

import { useState, useCallback } from "react";
import { useRouter } from "next/navigation";
import { Filters } from "../components/filters";
import { Table } from "../components/table";

export default function PeoplePage() {
  const router = useRouter();
  const [people, setPeople] = useState([
    { 
      id: 1, 
      name: "João Silva", 
      email: "joao@email.com", 
      document: "123.456.789-00", 
      personType: "pf",
      status: "active" 
    },
    { 
      id: 2, 
      name: "Empresa XYZ", 
      businessName: "Empresa XYZ Comércio e Serviços Ltda",
      email: "contato@xyz.com", 
      document: "12.345.678/0001-90", 
      personType: "pj",
      status: "inactive" 
    },
  ]);

  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(10);

  const handleFilter = useCallback((filters) => {
    console.log("Applied filters:", filters);
  }, []);

  const handleEdit = useCallback((person) => {
    console.log("Edit person:", person);
  }, []);

  const handleDelete = useCallback((id) => {
    console.log("Delete person:", id);
  }, []);

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
          <button
            onClick={() => router.push('/pessoas/nova')}
            className="px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 transition-colors duration-200"
          >
            Nova Pessoa
          </button>
        </div>

        <Filters onFilter={handleFilter} />

        <Table 
          people={people}
          onEdit={handleEdit}
          onDelete={handleDelete}
          currentPage={currentPage}
          itemsPerPage={itemsPerPage}
          totalItems={people.length}
          onPageChange={handlePageChange}
          onItemsPerPageChange={handleItemsPerPageChange}
        />
      </div>
    </div>
  );
} 