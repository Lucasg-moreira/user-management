"use client";

import { useState, useCallback } from "react";
import { useRouter } from "next/navigation";
import { formatCNPJ, formatCPF, validateCNPJ, validateCPF } from "@/utils/utils";
import IndividualPersonForm from "./components/IndividualPersonForm";
import CompanyPersonForm from "./components/CompanyPersonForm";

export default function NewPersonPage() {
  const router = useRouter();
  
  const [formData, setFormData] = useState({
    name: "",
    telephone: "",
    cpf: "",
    birthDate: "",
    companyName: "",
    fantasyName: "",
    cnpj: "",
    personType: "pf"
  });

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");

  const handleChange = useCallback((e) => {
    const { name, value } = e.target;

    if (name === 'cpf') {
      setFormData(prev => ({
        ...prev,
        [name]: formatCPF(value)
      }));
    } else if (name === 'cnpj') {
      setFormData(prev => ({
        ...prev,
        [name]: formatCNPJ(value)
      }));
    } else if (name === 'personType') {
      setFormData(prev => ({
        ...prev,
        personType: value,
        cpf: "",
        cnpj: "",
        companyName: value === 'pf' ? "" : prev.companyName,
        fantasyName: value === 'pf' ? "" : prev.fantasyName
      }));
    } else {
      setFormData(prev => ({
        ...prev,
        [name]: value
      }));
    }
  }, []);

  const validateForm = useCallback(() => {
    if (!formData.name || !formData.telephone) {
      setError("Por favor, preencha todos os campos obrigatórios");
      return false;
    }

    if (formData.personType === 'pf') {
      if (!formData.cpf || !formData.birthDate) {
        setError("Por favor, preencha todos os campos obrigatórios");
        return false;
      }
      if (!validateCPF(formData.cpf)) {
        setError("Por favor, insira um CPF válido");
        return false;
      }
    } else {
      if (!formData.companyName || !formData.fantasyName || !formData.cnpj) {
        setError("Por favor, preencha todos os campos obrigatórios");
        return false;
      }
      if (!validateCNPJ(formData.cnpj)) {
        setError("Por favor, insira um CNPJ válido");
        return false;
      }
    }
    return true;
  }, [formData]);

  const handleSubmit = useCallback(async (e) => {
    e.preventDefault();

    if (!validateForm()) {
      return;
    }

    try {
      setIsLoading(true);
      await new Promise(resolve => setTimeout(resolve, 1000));

      router.push("/pessoas");
    } catch (error) {
      setError("Ocorreu um erro ao salvar a pessoa");
      console.error("Error saving:", error);
    } finally {
      setIsLoading(false);
    }
  }, [formData, validateForm, router]);

  return (
    <div className="min-h-screen bg-gray-100 dark:bg-gray-900 py-8 px-4 sm:px-6 lg:px-8">
      <div className="max-w-2xl mx-auto">
        <div className="bg-white dark:bg-gray-800 rounded-lg shadow-lg p-6">
          <div className="flex justify-between items-center mb-6">
            <h1 className="text-2xl font-bold text-gray-900 dark:text-white">
              Novo cliente
            </h1>
            <button
              onClick={() => router.back()}
              className="text-gray-600 hover:text-gray-900 dark:text-gray-400 dark:hover:text-white"
            >
              Voltar
            </button>
          </div>

          {error && (
            <div className="mb-4 p-4 bg-red-50 dark:bg-red-900/50 border border-red-200 dark:border-red-800 rounded-lg text-sm text-red-600 dark:text-red-400">
              {error}
            </div>
          )}

          <form className="space-y-6">
            <div>
              <label htmlFor="personType" className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                Tipo de Pessoa *
              </label>
              <select
                id="personType"
                name="personType"
                value={formData.personType}
                onChange={handleChange}
                disabled={isLoading}
                className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent dark:bg-gray-700 dark:text-white disabled:opacity-50"
              >
                <option value="pf">Pessoa Física</option>
                <option value="pj">Pessoa Jurídica</option>
              </select>
            </div>

            {formData.personType === 'pf' ? (
              <IndividualPersonForm
                formData={formData}
                handleChange={handleChange}
                isLoading={isLoading}
              />
            ) : (
              <CompanyPersonForm
                formData={formData}
                handleChange={handleChange}
                isLoading={isLoading}
              />
            )}

            <div className="flex justify-end space-x-4">
              <button
                type="button"
                onClick={() => router.back()}
                disabled={isLoading}
                className="px-4 py-2 border border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 rounded-lg hover:bg-gray-50 dark:hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:opacity-50"
              >
                Cancelar
              </button>
              <button
                onClick={handleSubmit}
                type="button"
                disabled={isLoading}
                className="px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:opacity-50"
              >
                {isLoading ? "Salvando..." : "Salvar"}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
} 