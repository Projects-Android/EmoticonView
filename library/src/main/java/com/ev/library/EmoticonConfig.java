package com.ev.library;

import com.ev.library.decode.IDecoder;
import com.ev.library.decode.IPicDecoder;
import com.ev.library.getter.IEmoticonGetter;
import com.ev.library.strategy.IAssetsStrategyFactory;

import java.util.ArrayList;

/**
 * Created by EV on 2018/4/23.
 */
public class EmoticonConfig {

    private ArrayList<IEmoticonGetter> mGetters = new ArrayList<IEmoticonGetter>();
    private ArrayList<IDecoder> mDecoders = new ArrayList<IDecoder>();
    private ArrayList<IPicDecoder> mPicDecoders = new ArrayList<IPicDecoder>();
    private IAssetsStrategyFactory mAssetsStrategyFactory;

    public ArrayList<IPicDecoder> getPicDecoders() {
        return mPicDecoders;
    }

    public IAssetsStrategyFactory getAssetsStrategyFactory() {
        return mAssetsStrategyFactory;
    }

    public ArrayList<IEmoticonGetter> getGetters() {
        return mGetters;
    }

    public ArrayList<IDecoder> getDecoders() {
        return mDecoders;
    }

    public static class Builder {

        private EmoticonConfig mEmoticonConfig;

        public Builder() {
            mEmoticonConfig = new EmoticonConfig();
        }

        /**
         * add decoders
         * @param pDecoder
         * @return
         */
        public Builder addDecoders(IDecoder pDecoder) {
            mEmoticonConfig.mDecoders.add(pDecoder);
            return this;
        }

        /**
         * add getters
         * @param pGetter
         * @return
         */
        public Builder addGetters(IEmoticonGetter pGetter) {
            mEmoticonConfig.mGetters.add(pGetter);
            return this;
        }

        /**
         * set assets strategy
         * @param pFactory
         * @return
         */
        public Builder setAssetsStrategy(IAssetsStrategyFactory pFactory) {
            mEmoticonConfig.mAssetsStrategyFactory = pFactory;
            return this;
        }

        /**
         * add pic decoders
         * @param pPicDecoders
         * @return
         */
        public Builder addPicDecoders(IPicDecoder pPicDecoders) {
            mEmoticonConfig.mPicDecoders.add(pPicDecoders);
            return this;
        }

        public EmoticonConfig build() {
            return mEmoticonConfig;
        }
    }

}
