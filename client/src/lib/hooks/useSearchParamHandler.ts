
import { useSearchParams } from 'react-router-dom';

const useSearchParamHandler = (
  params: Record<string, any>,
) => {
  const [, setSearchParams] = useSearchParams();
  const url = new URLSearchParams(window.location.search);
  const k = Object.keys(params);
  for (let i = 0; i < k.length; i += 1) {
    const prop = k[i];
    if (params[prop] === "") {
      url.delete(prop);
    } else {
      url.set(prop, params[prop]!);
    }
  }
  setSearchParams(url);
};

export default useSearchParamHandler;
