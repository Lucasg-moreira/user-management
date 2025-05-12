
FROM node:24-alpine AS base

FROM base AS deps

WORKDIR /app

COPY package.json .
COPY package-lock.json .

RUN npm ci

COPY . .

RUN npm run build

FROM base AS production

WORKDIR /app

ENV NODE_ENV production

COPY --from=deps /app/public ./public
COPY --from=deps /app/package.json .

COPY --from=deps /app/.next/standalone ./
COPY --from=deps /app/next.config.mjs ./
COPY --from=deps /app/.next/static ./.next/static

EXPOSE 3000

ENV PORT 3000
ENV HOSTNAME "0.0.0.0"

CMD ["node", "server.js"]