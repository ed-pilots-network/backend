defmodule Eddn do
  @moduledoc """
  Documentation for `Eddn`.
  """

  @doc """
  Hello world.

  ## Examples

      iex> Eddn.hello()
      :world

  """
  def hello do
    :world
  end

  def start_listener() do
    DynamicSupervisor.start_child(
      Eddn.DynamicSupervisor,
      {Eddn.Listener, name: {:via, Registry, {Eddn.Registry, :eddn_edcd_io_listener, []}}}
    )
  end

  def registry_lookup(registry_key) do
    Registry.lookup(Eddn.Registry, registry_key)
  end

  def registry_read() do
    DynamicSupervisor.which_children(Eddn.DynamicSupervisor)
    |> Enum.map(fn {_a, pid, _worker, [module]} ->
      [key] = Registry.keys(Eddn.Registry, pid)
      %{"registry_key" => key, "pid" => pid, "module" => module}
    end)
  end

  def read_schema(path) do
    File.read!(path)
    |> Jason.decode!()
  end
end
