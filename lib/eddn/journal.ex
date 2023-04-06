defmodule Eddn.Journal do
  use Ecto.Schema

  schema "journals" do
    # TODO Need proper data shape for incoming journals
    field :schema, :string
    field :additionalProperties, :boolean
    field :definitions, :map
    field :eddn_id, :string
    field :properties, :map
    field :required, {:array, :string}
    field :type, :string
  end
end
