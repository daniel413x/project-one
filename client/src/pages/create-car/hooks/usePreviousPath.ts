import { useEffect, useRef } from "react";
import { useLocation } from "react-router-dom";

const usePreviousPath = () => {
  const location = useLocation();
  const prevLocation = useRef(location);
  useEffect(() => {
    prevLocation.current = location;
  }, [location]);
  return prevLocation.current.pathname;
};

export default usePreviousPath;
