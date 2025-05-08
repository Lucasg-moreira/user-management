"use client";

import { useState, useCallback } from "react";
import { useRouter } from "next/navigation";

const Filters = ({ onFilter }) => {
  const [filters, setFilters] = useState({
    name: "",
    email: "",
    document: "",
    personType: "all",
    status: "all"
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFilters(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onFilter(filters);
  };

  return (
    <div className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-lg mb-6">
      <h2 className="text-lg font-semibold text-gray-900 dark:text-white mb-4">Filtros</h2>
      <form onSubmit={handleSubmit} className="grid grid-cols-1 md:grid-cols-5 gap-4">
        <div>
          <label htmlFor="name" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
            Nome
          </label>
          <input
            type="text"
            id="name"
            name="name"
            value={filters.name}
            onChange={handleChange}
            className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
            placeholder="Buscar por nome"
          />
        </div>
        <div>
          <label htmlFor="email" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
            Email
          </label>
          <input
            type="email"
            id="email"
            name="email"
            value={filters.email}
            onChange={handleChange}
            className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
            placeholder="Buscar por email"
          />
        </div>
        <div>
          <label htmlFor="document" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
            CPF/CNPJ
          </label>
          <input
            type="text"
            id="document"
            name="document"
            value={filters.document}
            onChange={handleChange}
            className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
            placeholder="Buscar por CPF/CNPJ"
          />
        </div>
        <div>
          <label htmlFor="personType" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
            Tipo
          </label>
          <select
            id="personType"
            name="personType"
            value={filters.personType}
            onChange={handleChange}
            className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
          >
            <option value="all">Todos</option>
            <option value="pf">Pessoa Física</option>
            <option value="pj">Pessoa Jurídica</option>
          </select>
        </div>
        <div>
          <label htmlFor="status" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
            Status
          </label>
          <select
            id="status"
            name="status"
            value={filters.status}
            onChange={handleChange}
            className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
          >
            <option value="all">Todos</option>
            <option value="active">Ativo</option>
            <option value="inactive">Inativo</option>
          </select>
        </div>
        <div className="md:col-span-5 flex justify-end">
          <button
            type="submit"
            className="px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2 transition-colors duration-200"
          >
            Filtrar
          </button>
        </div>
      </form>
    </div>
  );
};

const PeopleTable = ({ people, onEdit, onDelete, currentPage, itemsPerPage, totalItems, onPageChange, onItemsPerPageChange }) => {
  const totalPages = Math.ceil(totalItems / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const endIndex = Math.min(startIndex + itemsPerPage, totalItems);
  const currentItems = people.slice(startIndex, endIndex);

  return (
    <div className="space-y-4">
      <div className="bg-white dark:bg-gray-800 rounded-lg shadow-lg overflow-hidden">
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
            <thead className="bg-gray-50 dark:bg-gray-700">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
                  Nome/Razão Social
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
                  Nome Fantasia
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
                  Email
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
                  Tipo
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
                  CPF/CNPJ
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
                  Status
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
                  Ações
                </th>
              </tr>
            </thead>
            <tbody className="bg-white dark:bg-gray-800 divide-y divide-gray-200 dark:divide-gray-700">
              {currentItems.map((person) => (
                <tr key={person.id}>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                    {person.personType === 'pf' ? person.name : person.businessName}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                    {person.personType === 'pj' ? person.name : '-'}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                    {person.email}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                    {person.personType === 'pf' ? 'Pessoa Física' : 'Pessoa Jurídica'}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                    {person.document}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${
                      person.status === 'active' 
                        ? 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200' 
                        : 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200'
                    }`}>
                      {person.status === 'active' ? 'Ativo' : 'Inativo'}
                    </span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                    <button
                      onClick={() => onEdit(person)}
                      className="text-indigo-600 hover:text-indigo-900 dark:text-indigo-400 dark:hover:text-indigo-300 mr-4"
                    >
                      Editar
                    </button>
                    <button
                      onClick={() => onDelete(person.id)}
                      className="text-red-600 hover:text-red-900 dark:text-red-400 dark:hover:text-red-300"
                    >
                      Excluir
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      <div className="flex items-center justify-between bg-white dark:bg-gray-800 px-4 py-3 sm:px-6 rounded-lg shadow-lg">
        <div className="flex items-center">
          <label htmlFor="itemsPerPage" className="mr-2 text-sm text-gray-700 dark:text-gray-300">
            Itens por página:
          </label>
          <select
            id="itemsPerPage"
            value={itemsPerPage}
            onChange={(e) => onItemsPerPageChange(Number(e.target.value))}
            className="px-2 py-1 border border-gray-300 dark:border-gray-600 rounded-md text-sm text-gray-700 dark:text-gray-300 bg-white dark:bg-gray-700"
          >
            <option value={5}>5</option>
            <option value={10}>10</option>
            <option value={20}>20</option>
            <option value={50}>50</option>
          </select>
          <span className="ml-4 text-sm text-gray-700 dark:text-gray-300">
            Mostrando {startIndex + 1} a {endIndex} de {totalItems} registros
          </span>
        </div>
        <div className="flex items-center space-x-2">
          <button
            onClick={() => onPageChange(1)}
            disabled={currentPage === 1}
            className="px-3 py-1 rounded-md text-sm font-medium text-gray-700 dark:text-gray-300 bg-white dark:bg-gray-700 border border-gray-300 dark:border-gray-600 disabled:opacity-50"
          >
            Primeira
          </button>
          <button
            onClick={() => onPageChange(currentPage - 1)}
            disabled={currentPage === 1}
            className="px-3 py-1 rounded-md text-sm font-medium text-gray-700 dark:text-gray-300 bg-white dark:bg-gray-700 border border-gray-300 dark:border-gray-600 disabled:opacity-50"
          >
            Anterior
          </button>
          <span className="px-3 py-1 text-sm text-gray-700 dark:text-gray-300">
            Página {currentPage} de {totalPages}
          </span>
          <button
            onClick={() => onPageChange(currentPage + 1)}
            disabled={currentPage === totalPages}
            className="px-3 py-1 rounded-md text-sm font-medium text-gray-700 dark:text-gray-300 bg-white dark:bg-gray-700 border border-gray-300 dark:border-gray-600 disabled:opacity-50"
          >
            Próxima
          </button>
          <button
            onClick={() => onPageChange(totalPages)}
            disabled={currentPage === totalPages}
            className="px-3 py-1 rounded-md text-sm font-medium text-gray-700 dark:text-gray-300 bg-white dark:bg-gray-700 border border-gray-300 dark:border-gray-600 disabled:opacity-50"
          >
            Última
          </button>
        </div>
      </div>
    </div>
  );
};

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
        <PeopleTable 
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