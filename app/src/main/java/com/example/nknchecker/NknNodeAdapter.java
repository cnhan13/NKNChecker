package com.example.nknchecker;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

public class NknNodeAdapter extends RecyclerView.Adapter<NknNodeAdapter.NknNodeViewHolder>
        implements FetchNknNodeDetailAsyncTask.CompleteListener, View.OnClickListener {

    private static final String TAG = NknNodeAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<NknNode> nknNodes;

    public NknNodeAdapter(Context context, ArrayList<NknNode> nknNodes) {
        this.context = context;
        this.nknNodes = nknNodes;
    }

    @Override
    public NknNodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nkn_node_list_item, parent, false);
        view.setOnClickListener(this);
        return new NknNodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NknNodeViewHolder holder, int position) {
        NknNode nknNode = nknNodes.get(position);
        holder.getItemView().setTag(position);

        GradientDrawable syncStateCircle = (GradientDrawable) holder.tvProposalSubmitted.getBackground();

        int syncStateColor = getSyncStateColor(nknNode);
        syncStateCircle.setColor(syncStateColor);

        String protocolVersionDisplay = formatProtocolVersion(nknNode.getProtocolVersion());
        String uptimeDisplay = formatUptime(nknNode.getUptime());
        String relayMessageCountDisplay = formatRelayMessageCount(nknNode.getRelayMessageCount());

        holder.tvProposalSubmitted.setText(String.valueOf(nknNode.getProposalSubmitted()));
        holder.tvHostService.setText(nknNode.getHostService());
        holder.tvIpAddress.setText(nknNode.getIpAddress());
        holder.tvName.setText(nknNode.getName());
        holder.tvProtocolVersion.setText(protocolVersionDisplay);
        holder.tvUptime.setText(uptimeDisplay);
        holder.tvRelayMessageCount.setText(relayMessageCountDisplay);
        holder.tvError.setText(nknNode.getError());
        if (nknNode.getError().isEmpty()) {
            holder.tvError.setVisibility(View.GONE);
        } else {
            holder.tvError.setVisibility(View.VISIBLE);
        }
    }

    private String formatProtocolVersion(int version) {
        return context.getString(R.string.protocol_version_placeholder, version);
    }

    private String formatUptime(long seconds) {
        int second = (int) (seconds % 60);
        int minutes = (int) (seconds / 60);
        int minute = (int) (minutes % 60);
        int hours = (int) (minutes / 60);
        int hour = (int) (hours % 24);
        int day = (int) (hours / 24);
        return context.getString(R.string.day_hour_minute_second_placeholder, day, hour, minute, second);
    }

    private String formatRelayMessageCount(int count) {
        return context.getString(R.string.message_count_placeholder, count);
    }

    @Override
    public int getItemCount() {
        return nknNodes.size();
    }

    @Override
    public void onComplete(JSONObject jsonObject, Integer position) {
        if (jsonObject == null || position == null) {
            return;
        }
        NknNode nknNode = nknNodes.get(position);

        JSONObject resultJSONObject = jsonObject.optJSONObject(NknNode.FIELD_RESULT);
        if (resultJSONObject != null) {
            String syncState = resultJSONObject.optString(NknNode.FIELD_SYNC_STATE);
            nknNode.setSyncState(syncState);
            if (syncState.equals(NknNode.SYNC_STATE_PERSIST_FINISHED)) {
                nknNode.setAddr(resultJSONObject.optString(NknNode.FIELD_ADDR));
                nknNode.setCurrTimeStamp(resultJSONObject.optLong(NknNode.FIELD_CURR_TIME_STAMP));
                nknNode.setHeight(resultJSONObject.optLong(NknNode.FIELD_HEIGHT));
                nknNode.setId(resultJSONObject.optString(NknNode.FIELD_ID));
                nknNode.setJsonRpcPort(resultJSONObject.optInt(NknNode.FIELD_JSON_RPC_PORT));
                nknNode.setProposalSubmitted(resultJSONObject.optInt(NknNode.FIELD_PROPOSAL_SUBMITTED));
                nknNode.setProtocolVersion(resultJSONObject.optInt(NknNode.FIELD_PROTOCOL_VERSION));
                nknNode.setPublicKey(resultJSONObject.optString(NknNode.FIELD_PUBLIC_KEY));
                nknNode.setRelayMessageCount(resultJSONObject.optInt(NknNode.FIELD_RELAY_MESSAGE_COUNT));
                nknNode.setUptime(resultJSONObject.optLong(NknNode.FIELD_UPTIME));
                nknNode.setVersion(resultJSONObject.optString(NknNode.FIELD_VERSION));
                nknNode.setWebsocketPort(resultJSONObject.optInt(NknNode.FIELD_WEBSOCKET_PORT));
            } else if (syncState.equals(NknNode.SYNC_STATE_OFFLINE)) {
                // set nothing
            }
        }
        nknNode.setError(jsonObject.optString(NknNode.FIELD_ERROR));
        notifyItemChanged(position);
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        fetchNodeDetailAt(position);
    }

    class NknNodeViewHolder extends RecyclerView.ViewHolder {

        View itemView;
        TextView tvProposalSubmitted;
        TextView tvHostService;
        TextView tvIpAddress;
        TextView tvName;
        TextView tvProtocolVersion;
        TextView tvUptime;
        TextView tvRelayMessageCount;
        TextView tvError;

        NknNodeViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;
            tvProposalSubmitted = (TextView) itemView.findViewById(R.id.tvProposalSubmitted);
            tvHostService = (TextView) itemView.findViewById(R.id.tvHostService);
            tvIpAddress = (TextView) itemView.findViewById(R.id.tvIpAddress);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvProtocolVersion = (TextView) itemView.findViewById(R.id.tvProtocolVersion);
            tvUptime = (TextView) itemView.findViewById(R.id.tvUptime);
            tvRelayMessageCount = (TextView) itemView.findViewById(R.id.tvRelayMessageCount);
            tvError = (TextView) itemView.findViewById(R.id.tvError);
        }

        public View getItemView() {
            return itemView;
        }
    }

    public void startFetchingNodesDetail() {
        if (nknNodes == null) {
            throw new NullPointerException("Adapter item list not initialized");
        }
        for (int i = 0; i < nknNodes.size(); i++) {
            fetchNodeDetailAt(i);
        }
    }

    private void fetchNodeDetailAt(int position) {
        nknNodes.get(position).resetNodePartially();
        notifyItemChanged(position);

        new FetchNknNodeDetailAsyncTask(this)
                .execute(nknNodes.get(position).getIpAddress(), String.valueOf(position));
    }

    private int getSyncStateColor(NknNode nknNode) {
        String syncState = nknNode.getSyncState();
        int colorResId;
        if (syncState != null && syncState.equals(NknNode.SYNC_STATE_PERSIST_FINISHED)) {
            int proposalSubmitted = nknNode.getProposalSubmitted();
            switch (proposalSubmitted) {
                case 0:
                    colorResId = R.color.proposalSubmitted0;
                    break;
                case 1:
                    colorResId = R.color.proposalSubmitted1;
                    break;
                case 2:
                    colorResId = R.color.proposalSubmitted2;
                    break;
                case 3:
                    colorResId = R.color.proposalSubmitted3;
                    break;
                case 4:
                    colorResId = R.color.proposalSubmitted4;
                    break;
                case 5:
                    colorResId = R.color.proposalSubmitted5;
                    break;
                case 6:
                    colorResId = R.color.proposalSubmitted6;
                    break;
                case 7:
                    colorResId = R.color.proposalSubmitted7;
                    break;
                case 8:
                    colorResId = R.color.proposalSubmitted8;
                    break;
                case 9:
                default:
                    colorResId = R.color.proposalSubmitted9plus;
            }
        } else {
            colorResId = R.color.offline;
        }
        // return color code from colorResId
        return ContextCompat.getColor(context, colorResId);
    }
}
