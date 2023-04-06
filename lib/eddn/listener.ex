defmodule Eddn.Listener do
  use GenServer, restart: :temporary

  def read_status(pid) do
    GenServer.call(pid, :read_status)
  end

  def handle_call(:read_status, _from, state) do
    {:reply, state, state}
  end

  def handle_continue(:consume, state) do
    consume()
    {:noreply, state}
  end

  def consume() do
    {:ok, context} = :erlzmq.context()
    {:ok, subscriber} = :erlzmq.socket(context, :sub)
    :erlzmq.setsockopt(subscriber, :subscribe, "")
    :erlzmq.setsockopt(subscriber, :rcvtimeo, 600_000)
    :erlzmq.connect(subscriber, "tcp://eddn.edcd.io:9500")
    loop(subscriber)
  end

  defp loop(subscriber) do
    with {:ok, data} <- :erlzmq.recv(subscriber) do
      z = :zlib.open()
      :zlib.inflateInit(z)
      uncompressed_data = :zlib.inflate(z, data)
      json = Jason.decode!(uncompressed_data)
      IO.inspect(json, label: "EDDN RECEIVED")
      :zlib.close(z)
      loop(subscriber)
    end
  end

  def init(state) do
    {:ok, state, {:continue, :consume}}
  end

  def start_link(args) do
    GenServer.start_link(__MODULE__, %{status: :ok}, name: args[:name])
  end
end
