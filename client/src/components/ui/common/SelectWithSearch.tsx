import LoadingSpinner from "@/components/ui/common/LoadingSpinner";
import { Input } from "@/components/ui/common/shadcn/input";
import {
  Select, SelectContent, SelectGroup, SelectItem, SelectTrigger,
  SelectValue,
} from "@/components/ui/common/shadcn/select";
import useDebounce from "@/lib/hooks/useDebounce";
import { GETManyRes } from "@/lib/types";
import { MagnifyingGlassIcon } from "@radix-ui/react-icons";
import {
  ChangeEvent, useEffect, useRef, useState,
} from "react";
import { useEventListener } from "usehooks-ts";
import useActiveElement from "@/lib/hooks/useActiveElement";
import { UseFormReturn } from "react-hook-form";

// define an object for any model that has a name and an id
// as we are using several where there are relationships
// such as make & car, model & car, color & car etc.
export type NamedObject = { name: string; id: number; };

type Form = UseFormReturn<any, any, undefined>;

interface SelectWithSearchProps {
  // this prop should handle any react-query hook that returns
  // rows of NamedObject-like objects (e.g. make, model)
  hook: (search?: string | undefined) => {
    data: GETManyRes<NamedObject> | undefined;
    isLoading: boolean;
  }
  form: Form;
  name: "color" | "make" | "model" | "owner";
}

function SelectWithSearch({
  hook,
  form,
  name,
}: SelectWithSearchProps) {
  const activeElement = useActiveElement();
  const [search, setSearch] = useState<string>("");
  const debouncedSearch = useDebounce(search, 500);
  const handleChangeSearch = (e: ChangeEvent<HTMLInputElement>) => {
    setSearch(e.target.value);
  };
  const {
    data,
    isLoading,
  } = hook(debouncedSearch);
  const noResults = data?.rows.length === 0;
  // select any model with name & id fields (color, make, model. etc)
  const onSelect = (namedObject: NamedObject) => {
    form.setValue(name, namedObject);
  };
  const inputRef = useRef<HTMLInputElement>(null);
  const selectRef = useRef<HTMLDivElement>(null);
  const isFirstHighlight = () => data && document.getElementById(`${name}-0`) === activeElement;
  const isLastHighlight = () => data && document.getElementById(`${name}-${data.rows.length - 1}`) === activeElement;
  const isInput = () => inputRef.current === activeElement;
  // handle Select component keyboard navigation
  // note that this is continued in the input's onKeyDown handler below
  const onKeyDownEventListener = (e: KeyboardEvent) => {
    // there will be several Select components
    // so make sure onKeyDown doesn't run outside of the engaged select component
    const isCurrentSelect = selectRef?.current?.contains(inputRef?.current);
    // find if the user presses an input-changing key
    const isAlphabeticalOrBackspaceKey = /^[A-Za-z]$|Backspace/.test(e.key);
    if (isCurrentSelect) {
      if (e.key === "Tab") {
        // tab back and forth between the input and the first search result
        if (activeElement !== inputRef.current) {
          inputRef.current?.focus();
        } else {
          document.getElementById(`${name}-0`)?.focus();
        }
      }
      // user pressed down arrow on last search result; focus input
      if (e.key === "ArrowDown" && isLastHighlight()) {
        inputRef.current?.focus();
      }
      // user pressed up arrow on first search result; focus input
      if (e.key === "ArrowUp" && isFirstHighlight()) {
        inputRef.current?.focus();
      }
      // user pressed down arrow while on input; focus first search result
      if ((e.key === "ArrowDown" || e.key === "Tab") && isInput()) {
        inputRef.current?.blur();
        document.getElementById(`${name}-0`)?.focus();
      }
      // user pressed up arrow while on input; focus last search result
      if (e.key === "ArrowUp" && isInput() && data) {
        inputRef.current?.blur();
        document.getElementById(`${name}-${data.rows.length - 1}`)?.focus();
      }
      // user pressed input-changing key; focus input
      if (isAlphabeticalOrBackspaceKey) {
        inputRef.current?.focus();
      }
    }
  };
  useEventListener("keydown", onKeyDownEventListener);
  const onKeyDownInput = (e: React.KeyboardEvent<HTMLInputElement>) => {
    // prevent Select component behavior of auto selecting based on keyboard input
    if (e.key !== "Tab" && e.key !== "ArrowDown" && e.key !== "ArrowUp") {
      e.stopPropagation();
    }
  };
  // refocus the input on any fetch
  // because the input ref can sometimes blur on search results updates
  useEffect(() => {
    inputRef.current?.focus();
  }, [data]);
  const selected = form.watch(name);
  return (
    <div className="flex">
      {/* TODO: create handler */}
      <Select onValueChange={(v) => form.setValue(name, JSON.parse(v))}>
        <SelectTrigger
          data-testid={`${name}-dropdown-trigger`}
          aria-label={selected.name || `Please select a ${name}`}
        >
          <SelectValue
            placeholder={selected.name}
          />
        </SelectTrigger>
        <SelectContent ref={selectRef}>
          <div className="relative">
            <MagnifyingGlassIcon className="absolute top-1/2 -translate-y-1/2 left-2.5" />
            <Input
              className="pl-8 border-0 focus:border-0 focus-visible:outline-0 focus-visible:ring-0"
              value={search}
              onChange={handleChangeSearch}
              onKeyDown={onKeyDownInput}
              ref={inputRef}
              data-testid={`${name}-dropdown-search-input`}
              autoFocus
            />
          </div>
          <SelectGroup>
            {isLoading ? <div className="py-1.5 pl-2 pr-8 text-sm"><LoadingSpinner /></div> : null}
            {!isLoading && noResults ? <span className="py-1.5 pl-2 pr-8 text-sm">No results</span> : null}
            {data?.rows.map((o, i) => (
              <SelectItem
                key={o.id}
                id={`${name}-${i}`}
                data-testid={`${name}-${i}`}
                value={JSON.stringify(o)}
                onClick={() => {
                  onSelect(o);
                }}
              >
                {o.name}
              </SelectItem>
            ))}
          </SelectGroup>
        </SelectContent>
      </Select>
    </div>
  );
}

export default SelectWithSearch;
