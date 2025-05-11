"use client";

import { useState, useCallback, useEffect } from "react";
import { useRouter } from "next/navigation";
import { formatCNPJ, formatCPF, removeFormat, validateCNPJ, validateCPF } from "@/utils/utils";
import { IndividualPersonForm } from "../../nova/components/IndividualPersonForm";
import { CompanyPersonForm } from "../../nova/components/CompanyPersonForm";
import { useFormError } from "@/hooks/useFormError";
import { AppError, ErrorCodes } from "@/utils/errorHandler";
import { api } from "@/utils/api";

const apiUrl = process.env.NEXT_PUBLIC_API_URL;


export function EditPersonForm({ id }) {
  const router = useRouter();
  const { error, fieldErrors, handleFormError, clearErrors, getFieldError } = useFormError();
  
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
  const [isInitialLoading, setIsInitialLoading] = useState(true);

  useEffect(() => {
    const fetchPerson = async () => {
        const data = await api.get(`${apiUrl}/client/${id}`);
        const cpfValue = data.cpf?.value || data.cpf || "";
        const cnpjValue = data.cnpj?.value || data.cnpj || "";
        
        setFormData({
          name: data.name || "",
          telephone: data.telephone || "",
          cpf: cpfValue ? formatCPF(cpfValue) : "",
          birthDate: data.birthDate || "",
          companyName: data.companyName || "",
          fantasyName: data.fantasyName || "",
          cnpj: cnpjValue ? formatCNPJ(cnpjValue) : "",
          personType: cpfValue ? "pf" : "pj"
        });
        
        setIsInitialLoading(false);
    };

    fetchPerson();
  }, [id, handleFormError]);

  const handleChange = useCallback((e) => {
    const { name, value } = e.target;
    clearErrors();

    if (name === 'cpf') {
      const numericValue = value.replace(/\D/g, '');
      setFormData(prev => ({
        ...prev,
        [name]: formatCPF(numericValue)
      }));
    } else if (name === 'cnpj') {
      const numericValue = value.replace(/\D/g, '');
      setFormData(prev => ({
        ...prev,
        [name]: formatCNPJ(numericValue)
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
  }, [clearErrors]);

  const validateForm = useCallback(() => {
    const errors = {};

    if (!formData.telephone) {
      errors.telephone = 'Telefone é obrigatório';
    }

    if (formData.personType === 'pf') {
      if (!formData.cpf) {
        errors.cpf = 'CPF é obrigatório';
      } else if (!validateCPF(formData.cpf)) {
        errors.cpf = 'CPF inválido';
      }

      if (!formData.name) {
        errors.name = 'Nome é obrigatório';
      }

      if (!formData.birthDate) {
        errors.birthDate = 'Data de nascimento é obrigatória';
      }
    } else {
      if (!formData.companyName) {
        errors.companyName = 'Razão social é obrigatória';
      }
      if (!formData.fantasyName) {
        errors.fantasyName = 'Nome fantasia é obrigatório';
      }
      if (!formData.cnpj) {
        errors.cnpj = 'CNPJ é obrigatório';
      } else if (!validateCNPJ(formData.cnpj)) {
        errors.cnpj = 'CNPJ inválido';
      }
    }

    if (Object.keys(errors).length > 0) {
      throw new AppError('Por favor, corrija os erros no formulário', ErrorCodes.VALIDATION_ERROR, errors);
    }

    return true;
  }, [formData]);

  const handleSubmit = useCallback(async (e) => {
    e.preventDefault();
    clearErrors();

    try {
      validateForm();
      setIsLoading(true);

      const personData = {
        ...formData,
        type: formData.personType,
        ...(formData.personType === 'pf' 
          ? { companyName: undefined, fantasyName: undefined, cnpj: undefined }
          : { cpf: undefined, birthDate: undefined }
        )
      };

      if (formData.personType === 'pf') {
        personData.cpf = removeFormat(personData.cpf)
        await api.put(`${apiUrl}/client/individual/${id}`, personData);

      } else {
        personData.cnpj = removeFormat(personData.cnpj)

        await api.put(`${apiUrl}/client/company/${id}`, personData);
      }

      router.push("/pessoas");
      
    } catch (error) {
      handleFormError(error);
      console.error('Error updating person:', error);
    } finally {
      setIsLoading(false);
    }
  }, [formData, validateForm, router, handleFormError, clearErrors, id]);

  if (isInitialLoading) {
    return (
      <div className="min-h-screen bg-gray-100 dark:bg-gray-900 py-8 px-4 sm:px-6 lg:px-8">
        <div className="max-w-2xl mx-auto">
          <div className="bg-white dark:bg-gray-800 rounded-lg shadow-lg p-6">
            <div className="animate-pulse">
              <div className="h-8 bg-gray-200 dark:bg-gray-700 rounded w-1/4 mb-6"></div>
              <div className="space-y-4">
                <div className="h-10 bg-gray-200 dark:bg-gray-700 rounded"></div>
                <div className="h-10 bg-gray-200 dark:bg-gray-700 rounded"></div>
                <div className="h-10 bg-gray-200 dark:bg-gray-700 rounded"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-100 dark:bg-gray-900 py-8 px-4 sm:px-6 lg:px-8">
      <div className="max-w-2xl mx-auto">
        <div className="bg-white dark:bg-gray-800 rounded-lg shadow-lg p-6">
          <div className="flex justify-between items-center mb-6">
            <h1 className="text-2xl font-bold text-gray-900 dark:text-white">
              Editar cliente
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

          <form onSubmit={handleSubmit} className="space-y-6">
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
                getFieldError={getFieldError}
              />
            ) : (
              <CompanyPersonForm
                formData={formData}
                handleChange={handleChange}
                isLoading={isLoading}
                getFieldError={getFieldError}
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
                type="submit"
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