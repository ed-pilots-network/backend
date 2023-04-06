defmodule Eddn.Repo.Migrations.CreateJournalTable do
  use Ecto.Migration

  def change do
    create table(:journals) do
      add :schema, :string
      add :additionalProperties, :boolean
      add :definitions, :map
      add :eddn_id, :string
      add :properties, :map
      add :required, {:array, :string}
      add :type, :string
    end
  end
end
