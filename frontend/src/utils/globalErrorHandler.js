import { AppError, ErrorCodes } from './errorHandler';

export const handleGlobalError = (error) => {
  if (error instanceof AppError) {
    return error;
  }

  if (!error.response) {
    return new AppError(
      'Erro de conexão. Verifique sua internet e tente novamente.',
      ErrorCodes.NETWORK_ERROR
    );
  }

  const status = error.response?.status;
  const data = error.response?.data;

  if (data?.error?.includes('duplicate key')) {
    return new AppError(
      'Este registro já existe no sistema.',
      ErrorCodes.VALIDATION_ERROR,
      { duplicate: true }
    );
  }

  if (data?.error?.includes('foreign key')) {
    return new AppError(
      'Não é possível realizar esta operação pois existem registros relacionados.',
      ErrorCodes.VALIDATION_ERROR
    );
  }

  if (data?.error?.includes('null value')) {
    return new AppError(
      'Campos obrigatórios não preenchidos.',
      ErrorCodes.VALIDATION_ERROR
    );
  }

  switch (status) {
    case 400:
      return new AppError(
        data?.error || 'Dados inválidos. Verifique os campos e tente novamente.',
        ErrorCodes.VALIDATION_ERROR,
        data?.errors
      );
    case 401:
      return new AppError(
        'Sessão expirada. Por favor, faça login novamente.',
        ErrorCodes.UNAUTHORIZED
      );
    case 403:
      return new AppError(
        'Você não tem permissão para realizar esta ação.',
        ErrorCodes.UNAUTHORIZED
      );
    case 404:
      return new AppError(
        'Recurso não encontrado.',
        ErrorCodes.NOT_FOUND
      );
    case 500:
      return new AppError(
        'Erro interno do servidor. Tente novamente mais tarde.',
        ErrorCodes.SERVER_ERROR
      );
    default:
      return new AppError(
        'Ocorreu um erro inesperado. Tente novamente mais tarde.',
        ErrorCodes.UNKNOWN_ERROR
      );
  }
}; 