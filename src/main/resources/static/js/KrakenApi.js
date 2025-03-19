function connectWithApi() {
    const PAIRS = [
        "BTC/USD", "ETH/USD", "SOL/USD", "XRP/USD", "ADA/USD",
        "DOGE/USD", "DOT/USD", "AVAX/USD", "LINK/USD", "MATIC/USD",
        "UNI/USD", "ATOM/USD", "LTC/USD", "ALGO/USD", "FIL/USD",
        "XTZ/USD", "EOS/USD", "AAVE/USD", "GRT/USD", "XLM/USD"
    ];

    // Имената на криптовалутите
    const CRYPTO_NAMES = {
        "BTC/USD": "Bitcoin",
        "XBT/USD":  "Bitcoin",
        "ETH/USD": "Ethereum",
        "SOL/USD": "Solana",
        "XRP/USD": "Ripple",
        "ADA/USD": "Cardano",
        "DOGE/USD": "Dogecoin",
        "XDG/USD": "Dogecoin",
        "DOT/USD": "Polkadot",
        "AVAX/USD": "Avalanche",
        "LINK/USD": "Chainlink",
        "MATIC/USD": "Polygon",
        "UNI/USD": "Uniswap",
        "ATOM/USD": "Cosmos",
        "LTC/USD": "Litecoin",
        "ALGO/USD": "Algorand",
        "FIL/USD": "Filecoin",
        "XTZ/USD": "Tezos",
        "EOS/USD": "EOS",
        "AAVE/USD": "Aave",
        "GRT/USD": "The Graph",
        "XLM/USD": "Stellar"
    };

    const ws = new WebSocket("wss://ws.kraken.com");

    // Обект за запазване на текущите цени
    let cryptoData = {};

    // Когато WebSocket се отвори
    ws.onopen = () => {
        console.log("Connected to Kraken WebSocket.");
        PAIRS.forEach(pair => {
            const subscribeMessage = {
                event: "subscribe",
                pair: [pair],
                subscription: { name: "ticker" }
            };
            ws.send(JSON.stringify(subscribeMessage));
        });
    };


    ws.onmessage = (event) => {
        try {
            const data = JSON.parse(event.data);

            if (Array.isArray(data) && data.length > 1) {
                const pair = data[3];
                const askPrice = data[1].a[0];
                const bidPrice = data[1].b[0];


                cryptoData[pair] = { ask: askPrice, bid: bidPrice };


                updateTable();
            }
        } catch (error) {
            console.error("Error processing WebSocket message:", error);
        }
    };


    function updateTable() {
        const tableBody = document.getElementById("crypto-table");

        Object.entries(cryptoData).forEach(([pair, prices]) => {
            let row = document.querySelector(`tr[data-pair="${pair}"]`);

            if (!row) {
                row = document.createElement("tr");
                row.setAttribute("data-pair", pair);
                row.innerHTML = `
                    <td>${CRYPTO_NAMES[pair]}</td>
                    <td>${pair}</td>
                    <td class="bid">${prices.bid}</td>
                    <td class="ask">${prices.ask}</td>
                    <td>
                        <form action="/buy" method="POST">
                            <input type="hidden" name="pair" value="${pair}">
                            <input type="hidden" class="hidden-ask" name="ask" value="${prices.ask}">
                            <label for="quantity-${pair}">Quantity:</label>
                            <input type="number" id="quantity-${pair}" name="quantity" value="1" min="0.0001" step="0.0001" required
                               oninvalid="this.setCustomValidity('Quantity must be a valid number')"
       oninput="this.setCustomValidity('')"
                            >
                            <button type="submit">Buy</button>
                        </form>
                        <form action="/sell" method="POST" style="display:inline-block;">
                            <input type="hidden" name="pair" value="${pair}">
                         <input type="hidden"  class="hidden-bid" name="bid" value="${prices.bid}">
                            <label for="sell-quantity-${pair}">Quantity:</label>
                            <input type="number" id="sell-quantity-${pair}" name="quantity" value="1" min="0.0001" step="0.0001" required
                            oninvalid="this.setCustomValidity('Quantity must be a valid number')"
       oninput="this.setCustomValidity('')"
                            >
                            <button type="submit">Sell</button>
                        </form>
                    </td>
                `;
                tableBody.appendChild(row);
            } else {
                let bidCell = row.querySelector(".bid");
                let askCell = row.querySelector(".ask");
                let hiddenBid = row.querySelector(".hidden-bid");
                let hiddenAsk = row.querySelector(".hidden-ask");

                if (bidCell.textContent !== prices.bid || askCell.textContent !== prices.ask) {
                    bidCell.textContent = prices.bid;
                    askCell.textContent = prices.ask;
                    hiddenBid.value = prices.bid;
                    hiddenAsk.value = prices.ask;
                }
            }
        });

        rows.forEach(row => tableBody.appendChild(row));
    }


}

connectWithApi();