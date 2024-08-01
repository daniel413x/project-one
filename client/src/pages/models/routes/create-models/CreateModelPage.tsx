import {
  ChevronLeft,
  CloudUpload,
  ComputerIcon,
  Trash2,
} from "lucide-react";
import Meta from "@/components/misc/Meta";
import PageHeader from "@/components/ui/common/PageHeader";
import ContentFrame from "@/components/ui/common/ContentFrame";
import { useNavigate, useParams } from "react-router-dom";
import { MODELS_ROUTE, CREATE_ROUTE } from "@/lib/consts";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Form, FormControl, FormField, FormItem, FormLabel,
  FormMessage,
} from "@/components/ui/common/shadcn/form";
import { UseFormReturn, useForm } from "react-hook-form";
import * as z from "zod";
import { Button } from "@/components/ui/common/shadcn/button";
import { Input } from "@/components/ui/common/shadcn/input";
import { toast } from "sonner";
import { useGetMakesForFormField } from "@/lib/api/MakesApi";
import {
  useCreateModel, useGetCarsCount, useGetModel, useUpdateModel,
} from "@/lib/api/ModelsApi";
import {
  Model,
  namedObjectSchema,
} from "@/lib/types";
import { useEffect } from "react";
import useReturnToQueryResultsCallback from "@/lib/hooks/useReturnToQueryResultsCallback";
import SelectWithSearch from "@/components/ui/common/SelectWithSearch";
import DeleteModelAlertDialog from "../../components/DeleteModelAlertDialog";

const formSchema = z.object({
  name: z.string().min(1),
  make: namedObjectSchema,
});

export type ModelForm = UseFormReturn<z.infer<typeof formSchema>, any, undefined>;

export type ModelFormValues = z.infer<typeof formSchema>;

function CreateModelPage() {
  const navigate = useNavigate();
  const {
    id,
  } = useParams();
  const isCreatePage = id === CREATE_ROUTE;
  const form = useForm<ModelFormValues>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      name: "",
      make: { id: 0, name: "" },
    },
  });
  // initialize with values of fetched model if there is one
  // otherwise initialize with empty values
  const resetForm = (model?: Model) => {
    form.setValue("name", model?.name || "");
    form.setValue("make", model?.make || { name: "", id: NaN });
  };
  const {
    fetchedModel,
  } = useGetModel(isCreatePage ? null : id);
  useEffect(() => {
    if (fetchedModel) {
      resetForm(fetchedModel);
    } else {
      resetForm();
    }
  }, [fetchedModel]);
  const {
    createModel,
  } = useCreateModel();
  const {
    updateModel,
  } = useUpdateModel(id!);
  const onSubmit = async (values: z.infer<typeof formSchema>) => {
    try {
      if (fetchedModel) {
        await updateModel(values);
        toast.success("Model updated");
      } else {
        const model = await createModel(values);
        navigate(`/${MODELS_ROUTE}/${model.id}`, { replace: true });
        resetForm(model);
        toast.success("New model created");
      }
    } catch (error: any) {
      toast.error(error.response.data || "Something went wrong...");
    }
  };
  const blockForm = form.formState.isSubmitting;
  const {
    wereSearchResults,
    onPressBackButton,
  } = useReturnToQueryResultsCallback(MODELS_ROUTE);
  const { isSubmitting } = form.formState;
  const pageHeaderText = isCreatePage ? "Create new model" : `Edit ${fetchedModel?.name}`;
  const {
    data: carsCount,
  } = useGetCarsCount(fetchedModel?.name);
  return (
    <Meta title={pageHeaderText}>
      <main>
        <PageHeader header={pageHeaderText} icon={<ComputerIcon />} />
        <ContentFrame mt>
          <div className="flex flex-col max-w-4xl  m-auto">
            <div className="grid grid-cols-2 gap-3">
              <Button
                type="button"
                disabled={blockForm}
                variant="outline"
                className="justify-start mb-4 ps-0 font-bold"
                onClick={onPressBackButton}
              >
                <ChevronLeft />
                {wereSearchResults ? "Return to search results" : "Exit"}
              </Button>
            </div>
            <Form {...form}>
              <form className="flex flex-col" onSubmit={form.handleSubmit(onSubmit)}>
                <FormField
                  control={form.control}
                  name="make"
                  key="make"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>
                        Make
                        <span className="text-gray-500">
                          {" "}
                          &#40;select one&#41;
                        </span>
                      </FormLabel>
                      <SelectWithSearch
                        hook={useGetMakesForFormField}
                        form={form}
                        name={field.name}
                      />
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="name"
                  key="name"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>
                        Name
                        <span className="text-gray-500">
                          {" "}
                          &#40;alphanumeric&#41;
                        </span>
                      </FormLabel>
                      <FormControl>
                        <Input
                          disabled={isSubmitting}
                          placeholder="Name"
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <div className=" mt-6 grid sm:grid-cols-3 gap-3">
                  <Button
                    disabled={blockForm}
                    type="submit"
                  >
                    <CloudUpload className="mr-1.5" strokeWidth={3} size={18} />
                    {!fetchedModel ? "Create" : "Save"}
                  </Button>
                  {!fetchedModel ? null : (
                    <DeleteModelAlertDialog
                      model={fetchedModel}
                      carsCount={carsCount}
                      returnTo={`/${MODELS_ROUTE}`}
                    >
                      <Button
                        type="button"
                        disabled={blockForm}
                        variant="destructive"
                      >
                        <Trash2 />
                        Delete
                      </Button>
                    </DeleteModelAlertDialog>
                  )}
                </div>
              </form>
            </Form>
          </div>
        </ContentFrame>
      </main>
    </Meta>
  );
}

export default CreateModelPage;
