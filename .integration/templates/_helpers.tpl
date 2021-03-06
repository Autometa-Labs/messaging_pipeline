{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "playground.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "playground.fullname" -}}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- $name := default .Chart.Name .Values.nameOverride -}}
{{- if contains $name .Release.Name -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}
{{- end -}}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "playground.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Common labels
*/}}
{{- define "playground.labels" -}}
helm.sh/chart: {{ include "playground.chart" . }}
{{ include "playground.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end -}}

{{/*
Selector labels
*/}}
{{- define "playground.selectorLabels" -}}
app.kubernetes.io/name: {{ include "playground.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end -}}

{{/*
Create the name of the service account to use
*/}}
{{- define "playground.serviceAccountName" -}}
{{- if .Values.serviceAccount.create -}}
    {{ default (include "playground.fullname" .) .Values.serviceAccount.name }}
{{- else -}}
    {{ default "default" .Values.serviceAccount.name }}
{{- end -}}
{{- end -}}

{{- define "playground.zookeeper" -}}
{{- printf "%s-%s" .Release.Name "zookeeper" | trunc 63 -}}
{{- end -}}

{{- define "playground.zookeeper-svc" -}}
{{- printf "%s-%s" .Release.Name "zookeeper-svc" | trunc 63 -}}
{{- end -}}

{{- define "playground.kibana" -}}
{{- printf "%s-%s" .Release.Name "kibana" | trunc 63 -}}
{{- end -}}

{{- define "playground.kibana-svc" -}}
{{- printf "%s-%s" .Release.Name "kibana-svc" | trunc 63 -}}
{{- end -}}

{{- define "playground.elasticsearch" -}}
{{- printf "%s-%s" .Release.Name "elasticsearch" | trunc 63 -}}
{{- end -}}

{{- define "playground.elasticsearch-svc" -}}
{{- printf "%s-%s" .Release.Name "elasticsearch-svc" | trunc 63 -}}
{{- end -}}

{{- define "playground.cassandra" -}}
{{- printf "%s-%s" .Release.Name "cassandra" | trunc 63 -}}
{{- end -}}

{{- define "playground.cassandra-svc" -}}
{{- printf "%s-%s" .Release.Name "cassandra-svc" | trunc 63 -}}
{{- end -}}

{{- define "playground.kafka" -}}
{{- printf "%s-%s" .Release.Name "kafka" | trunc 63 -}}
{{- end -}}

{{- define "playground.kafka-svc" -}}
{{- printf "%s-%s" .Release.Name "kafka-svc" | trunc 63 -}}
{{- end -}}

{{- define "playground.producer" -}}
{{- printf "%s-%s" .Release.Name "producer" | trunc 63 -}}
{{- end -}}

{{- define "playground.esproc" -}}
{{- printf "%s-%s" .Release.Name "esproc" | trunc 63 -}}
{{- end -}}

{{- define "playground.cassandraproc" -}}
{{- printf "%s-%s" .Release.Name "cassandraproc" | trunc 63 -}}
{{- end -}}