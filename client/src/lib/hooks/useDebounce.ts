
import { useEffect, useState } from 'react';

const useDebounce = <T>(
  value: T,
  delay: number,
  callback?: (debouncedValue: T) => void,
): T => {
  const [debouncedValue, setDebouncedValue] = useState<T>(value);
  useEffect(() => {
    const handler = setTimeout(() => {
      if (callback) {
        callback(debouncedValue);
      }
      setDebouncedValue(value);
    }, delay);
    return () => {
      clearTimeout(handler);
    };
  }, [value, delay]);
  return debouncedValue;
};

export default useDebounce;
