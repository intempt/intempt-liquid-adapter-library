# Base image pinned by digest (INT-6457): a moved tag cannot change what is built.
# Dependabot (.github/dependabot.yml, docker ecosystem) proposes digest/version bumps as PRs.
FROM alpine:3.24.4@sha256:93b41ef40b1677fce8fcc64ff4af09daddfacf5040afde618bc46c1e2d1cc7d4

RUN mkdir -p /usr/local/etc \
  && { \
    echo 'install: --no-document'; \
    echo 'update: --no-document'; \
  } >> /etc/gemrc

RUN apk update && apk add --no-cache \
  ruby \
  ruby-bundler \
  ruby-bigdecimal \
	ruby-dev \
	build-base \
  libssl3 \
  libc6-compat

# liquid 5.x requires base64, which is no longer a default gem in Ruby 3.4.
RUN gem install base64 liquid
# debase 0.2.9 (ruby-debug-ide's native dependency) adds -Werror=incompatible-pointer-types, which breaks on
# Ruby 3.4 headers; ARCH_FLAG is the last part of the extension's CFLAGS, so this override wins.
RUN MAKEFLAGS="ARCH_FLAG=-Wno-error=incompatible-pointer-types" gem install debase \
  && gem install ruby-debug-ide

RUN mkdir -p /srv/liquid
WORKDIR /srv/liquid
VOLUME  /srv/liquid

ENTRYPOINT ["ruby"]
