import { useState, useCallback } from 'react';
import { handleError, ErrorCodes } from '@/utils/errorHandler';

export const useFormError = () => {
  const [error, setError] = useState(null);
  const [fieldErrors, setFieldErrors] = useState({});

  const handleFormError = useCallback((error) => {
    const handledError = handleError(error);
    
    if (handledError.code === ErrorCodes.VALIDATION_ERROR && handledError.details) {
      setFieldErrors(handledError.details);
      setError(null);
    } else {
      setError(handledError.message);
      setFieldErrors({});
    }
  }, []);

  const clearErrors = useCallback(() => {
    setError(null);
    setFieldErrors({});
  }, []);

  const getFieldError = useCallback((fieldName) => {
    return fieldErrors[fieldName] || null;
  }, [fieldErrors]);

  return {
    error,
    fieldErrors,
    handleFormError,
    clearErrors,
    getFieldError
  };
}; 